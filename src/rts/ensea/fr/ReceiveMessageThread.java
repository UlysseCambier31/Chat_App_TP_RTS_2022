package rts.ensea.fr;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceiveMessageThread extends  java.lang.Thread{
    private DatagramSocket socket;
    private String username;
    private VBox conversationGuiObject;
    private ScrollPane scrollGuiObject;
    public ReceiveMessageThread(DatagramSocket socket, String username) {
        this.socket = socket;
        this.username = username;
        this.conversationGuiObject = null;
        this.scrollGuiObject = null;
    }
    public ReceiveMessageThread(DatagramSocket socket, String username,VBox conversationGuiObject, ScrollPane scrollGuiObject) {
        this.socket = socket;
        this.username = username;
        this.conversationGuiObject = conversationGuiObject;
        this.scrollGuiObject = scrollGuiObject;
    }

    public void run() {
        while (true) {
            if (conversationGuiObject == null) {
                String message = null;
                try {
                    message = awaitMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (message != null) {

                    System.out.println(message);
                }
            } else {
                String[] message = null;
                try {
                    message = awaitMessageGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (message != null) {
                        String[] finalMessage = message;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                    Text nameGuiObject = new Text(finalMessage[1]);
                                    nameGuiObject.setStyle("-fx-font-size : 8px;");
                                    nameGuiObject.setFill(Color.BLACK);
                                    nameGuiObject.setWrappingWidth(200);
                                    conversationGuiObject.getChildren().add(nameGuiObject);

                                    Text contentGuiObject = new Text(finalMessage[2]);
                                    HBox hbox = new HBox(10);
                                if(finalMessage[0].equals("own")){
                                    contentGuiObject.setStyle("-fx-font-size : 12px;-fx-background-radius: 0 0 18 18; -fx-border-radius: 0 0 18 18;-fx-text-inner-color:#0084ff;");
                                    contentGuiObject.setFill(Color.WHITE);
                                    hbox.setBackground(new Background(new BackgroundFill(Paint.valueOf("#0084ff"),new CornerRadii(5),null)));
                                } else {
                                    contentGuiObject.setStyle("-fx-font-size : 12px;-fx-background-radius: 0 0 18 18; -fx-border-radius: 0 0 18 18;-fx-text-inner-color:#e6ecf0;");
                                    contentGuiObject.setFill(Color.BLACK);
                                    hbox.setBackground(new Background(new BackgroundFill(Paint.valueOf("#e6ecf0"),new CornerRadii(5),null)));
                                }
                                    contentGuiObject.setWrappingWidth(200);
                                    hbox.setAlignment(Pos.BOTTOM_RIGHT);
                                    hbox.getChildren().add(contentGuiObject);
                                    conversationGuiObject.getChildren().add(hbox);

                                    Text timeGuiObject = new Text(finalMessage[3]);
                                    timeGuiObject.setStyle("-fx-font-size : 8px;");
                                    timeGuiObject.setFill(Color.BLACK);
                                    timeGuiObject.setWrappingWidth(200);
                                    conversationGuiObject.getChildren().add(timeGuiObject);
                                    scrollGuiObject.layout();
                                    scrollGuiObject.setVvalue(1.0);
                            }
                        });
                    }
                }
            }
        }


    public String awaitMessage() throws IOException {
        int maxEncodedSize = 1024;
        byte[] buffer = new byte[maxEncodedSize];
        DatagramPacket packet= new DatagramPacket(buffer,buffer.length);
        socket.receive(packet);
        String serialized_data = new String(packet.getData(), StandardCharsets.UTF_8);
        Message message = new Message(new JSONObject(serialized_data));
        //if(!message.getUser().getName().equals(username))
        return "@"+message.getUser().getName()+" : "+message.getContent()+"\n"+message.getTime();
    }

    public String[] awaitMessageGUI() throws IOException {
        int maxEncodedSize = 1024;
        byte[] buffer = new byte[maxEncodedSize];
        DatagramPacket packet= new DatagramPacket(buffer,buffer.length);
        socket.receive(packet);
        String serialized_data = new String(packet.getData(), StandardCharsets.UTF_8);
        Message message = new Message(new JSONObject(serialized_data));
        if(message.getUser().getName().equals(username)) {
            return new String[] {"own",message.getUser().getName(),message.getContent(),message.getTime()};
        }  else {
            return new String[] {"",message.getUser().getName(),message.getContent(),message.getTime()};
        }
    }
}
