package rts.ensea.fr;

import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReceiveMessageThread extends  java.lang.Thread{
    private DatagramSocket socket;
    public ReceiveMessageThread(DatagramSocket socket) {
        this.socket = socket;
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
        String serialized_data = new String(packet.getData(),packet.getOffset(), maxEncodedSize);
        Message message = new Message(new JSONObject(serialized_data));
        InetAddress senderIP = message.getUser().getNetInfo().getAddress();
        int senderPort = message.getUser().getNetInfo().getPort();
        if(!(InetAddress.getLocalHost().getHostAddress().equals(senderIP.getHostAddress())&&(socket.getLocalPort()==senderPort))){
            return message.getUser().getName()+" : "+message.getContent()+"\n\t"+message.getTime();
        }
        return null;
    }
}
