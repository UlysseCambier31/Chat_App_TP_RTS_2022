package rts.ensea.fr;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.json.JSONObject;

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
    public ReceiveMessageThread(DatagramSocket socket, String username) {
        this.socket = socket;
        this.username = username;
        this.conversationGuiObject = null;
    }
    public ReceiveMessageThread(DatagramSocket socket, String username,VBox conversationGuiObject) {
        this.socket = socket;
        this.username = username;
        this.conversationGuiObject = conversationGuiObject;
    }

    public void run() {
        while (true) {
            String message = null;
            try {
                message = awaitMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(message!=null) {
                if (conversationGuiObject==null) {
                    System.out.println(message);
                } else {
                    String finalMessage = message;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Text messageGuiObject = new Text(finalMessage);
                            messageGuiObject.setWrappingWidth(150);
                            conversationGuiObject.getChildren().add(messageGuiObject);
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
        //if(!message.getUser().getName().equals(username) && conversationUiObject==null){
        return "@"+message.getUser().getName()+" : "+message.getContent()+"\n"+message.getTime();

    }
}
