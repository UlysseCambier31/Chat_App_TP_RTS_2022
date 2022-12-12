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

/**
 * <p>
 * This class represents a Thread that receive message on the application.
 * This class heavily rely on the DatagramSocket class.
 * </p>
 * <p>
 * Example :</p>
 * <code>
 * ReceiveMessageThread receiveMessageThread = new ReceiveMessageThread(socket,username,conversation,scroll);
 * receiveMessageThread.start();
 * </code>
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see DatagramSocket
 * @see VBox
 * @see ScrollPane
 */
public class ReceiveMessageThread extends  java.lang.Thread{
    private DatagramSocket socket;
    private String username;
    private VBox conversationGuiObject;
    private ScrollPane scrollGuiObject;

    /**
     * Constructs a Thread for receiving message on the GUI Application.
     * @param socket a DatagramSocket for receiving packets.
     * @param username the name of the user of the client.
     * @param conversationGuiObject the conversation object of the GUI Application.
     * @param scrollGuiObject the scroll object of the GUI Application.
     * @see ChatApplication
     * @see DatagramSocket
     * @see VBox
     * @see ScrollPane
     */
    public ReceiveMessageThread(DatagramSocket socket, String username,VBox conversationGuiObject, ScrollPane scrollGuiObject) {
        this.socket = socket;
        this.username = username;
        this.conversationGuiObject = conversationGuiObject;
        this.scrollGuiObject = scrollGuiObject;
    }

    /**
     * Constructs a Thread for receiving message.
     * @param socket  a DatagramSocket for receiving packets.
     * @param username the name of the user of the client.
     */
    public ReceiveMessageThread(DatagramSocket socket, String username) {
        this(socket,username,null,null);
    }

    /**
     * Receive a packet on the socket.
     * @return  The content of the packet red on the socket.
     * @throws IOException Throws an IOException.
     */
    public String receivePacket() throws IOException {
        int maxEncodedSize = 1024;
        byte[] buffer = new byte[maxEncodedSize];
        DatagramPacket packet= new DatagramPacket(buffer,buffer.length);
        socket.receive(packet);
        return new String(packet.getData(), StandardCharsets.UTF_8);
    }

    /**
     * Receive a message.
     * @return  The Message object received.
     * @throws IOException Throws an IOException.
     * @see Message
     * @see Payload
     */
    public Message receiveMessage() throws IOException {
        String serialized_data = receivePacket();
        Payload payload = new Payload(new JSONObject(serialized_data));
        return new Message(new JSONObject(payload.getArgs()));
    }

    /**
     * Await for the reception of the next message.
     * @return  A string formatted from the content of the received message.
     * @throws IOException Throws an IOException.
     * @see Message
     */
    public String awaitMessage() throws IOException {
        Message message = receiveMessage();
        return "@"+message.getUser().getName()+" : "+message.getContent()+"\n"+message.getTime();
    }

    /**
     * Await for the reception of the next message for the GUI Application.
     * @return  A string list of the content of the received message.
     * @throws IOException Throws an IOException.
     * @see Message
     * @see ChatApplication
     */
    public String[] awaitMessageGUI() throws IOException {
        Message message = receiveMessage();
        if(message.getUser().getName().equals(username)) {
            return new String[] {"own",message.getUser().getName(),message.getContent(),message.getTime()};
        }  else {
            return new String[] {"",message.getUser().getName(),message.getContent(),message.getTime()};
        }
    }

    /**
     * Run the Thread when the method start() is called.
     * Await for new message in loop and print them or update the GUI Application.
     * @see ChatApplication
     */
    public void run() {
        while (true) {
            if (conversationGuiObject == null) { // Case in which the ChatClient is executed on cmd.
                String message = null;
                try {
                    message = awaitMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (message != null) {
                    System.out.println(message);
                }
            } else { // Case in which the ChatClient is executed on the GUI Application
                String[] message = null;
                try {
                    message = awaitMessageGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (message != null) {
                    String[] finalMessage = message;
                    // It is quite difficult to update GUI when outside of the JavaFX graphical Thread. We need to use Platform.runlater() to execute the code on the JavaFX Thread.
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            int textWrappingWidth = 200;
                            // We update each part of the text with styling.
                            Text nameGuiObject = new Text(finalMessage[1]);
                            nameGuiObject.setStyle("-fx-font-size : 12px;");
                            nameGuiObject.setFill(Color.BLACK);
                            nameGuiObject.setWrappingWidth(textWrappingWidth);
                            conversationGuiObject.getChildren().add(nameGuiObject);

                            Text contentGuiObject = new Text(finalMessage[2]);
                            HBox hbox = new HBox(10);
                            //We change the background color depending on the ownership of the message received.
                            if(finalMessage[0].equals("own")){
                                contentGuiObject.setStyle("-fx-font-size : 16px;");
                                contentGuiObject.setFill(Color.WHITE);
                                hbox.setBackground(new Background(new BackgroundFill(Paint.valueOf("#0084ff"),new CornerRadii(5),null)));
                            } else {
                                contentGuiObject.setStyle("-fx-font-size : 16px;");
                                contentGuiObject.setFill(Color.BLACK);
                                hbox.setBackground(new Background(new BackgroundFill(Paint.valueOf("#e6ecf0"),new CornerRadii(5),null)));
                            }
                            contentGuiObject.setWrappingWidth(textWrappingWidth);
                            hbox.setAlignment(Pos.BOTTOM_RIGHT);
                            hbox.getChildren().add(contentGuiObject);
                            conversationGuiObject.getChildren().add(hbox);

                            Text timeGuiObject = new Text(finalMessage[3]);
                            timeGuiObject.setStyle("-fx-font-size : 12px;");
                            timeGuiObject.setFill(Color.BLACK);
                            timeGuiObject.setWrappingWidth(textWrappingWidth);
                            conversationGuiObject.getChildren().add(timeGuiObject);
                            scrollGuiObject.layout();
                            scrollGuiObject.setVvalue(1.0);
                        }
                    });
                }
            }
        }
    }
}
