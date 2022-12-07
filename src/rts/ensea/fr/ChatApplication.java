package rts.ensea.fr;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ChatApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Join Chat");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Join Chat");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 1, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Button joinbutton = new Button("Join");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(joinbutton);
        grid.add(hbBtn, 1, 4);

        ChatClient client = null;
        try {
            client = new ChatClient(8080, InetAddress.getByName("10.10.25.25"));
        } catch(ArrayIndexOutOfBoundsException | UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        ChatClient finalClient = client;
        joinbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ScrollPane scroll = new ScrollPane();
                VBox conversation = new VBox(10);
                scroll.setContent(conversation);
                conversation.setAlignment(Pos.BOTTOM_LEFT);
                String username = userTextField.getText();
                ReceiveMessageThread receiveMessageHandlerThread = new ReceiveMessageThread(finalClient.getSocket(),username,conversation,scroll);
                receiveMessageHandlerThread.start();
                try {
                    finalClient.send("Hi I'm "+username+" ! I've just arrived in the server !", username);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                joinbutton.setDisable(true);
                GridPane secondGrid = new GridPane();
                secondGrid.setAlignment(Pos.BOTTOM_CENTER);
                secondGrid.setHgap(10);
                secondGrid.setVgap(5);
                secondGrid.setPadding(new Insets(25, 25, 25, 25));

                TextField userInputField = new TextField();

                Button sendButton = new Button();
                sendButton.setText("➤");
                sendButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String text = userInputField.getText();
                        try {
                            finalClient.send(text, username);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        userInputField.setText("");
                    }
                });
                userInputField.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String text = userInputField.getText();
                        try {
                            finalClient.send(text, username);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        userInputField.setText("");
                    }
                });

                HBox hbBtn2 = new HBox(10);
                hbBtn2.setAlignment(Pos.CENTER);
                hbBtn2.getChildren().add(sendButton);

                GridPane gridUserInput = new GridPane();
                gridUserInput.setAlignment(Pos.CENTER);
                gridUserInput.add(userInputField,0,0);
                gridUserInput.add(hbBtn2,1,0);

                secondGrid.add(scroll,0,0);
                secondGrid.add(gridUserInput, 0, 1);
                Scene secondScene = new Scene(secondGrid, 300, 500);


                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Chat Conversation");
                newWindow.setScene(secondScene);

                // Set position of second window, related to primary window.
                newWindow.setX(primaryStage.getX()+100);
                newWindow.setY(primaryStage.getY()+10);

                newWindow.show();
            }
        });

        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
