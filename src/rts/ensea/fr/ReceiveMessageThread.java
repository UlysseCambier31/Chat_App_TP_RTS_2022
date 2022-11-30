package rts.ensea.fr;

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
    public ReceiveMessageThread(DatagramSocket socket, String username) {
        this.socket = socket;
        this.username = username;
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
                System.out.println(message);
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
        if(!message.getUser().getName().equals(username)){
            return "@"+message.getUser().getName()+" : "+message.getContent()+"\n\t"+message.getTime();
        }
        return null;
    }
}
