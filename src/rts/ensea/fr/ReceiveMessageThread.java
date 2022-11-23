package rts.ensea.fr;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
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
        String data = new String(packet.getData(),packet.getOffset(), maxEncodedSize);
        String[] tmp = data.split("\\$\\*\\$");
        InetAddress senderIP = InetAddress.getByName(tmp[1].replace("/",""));
        int senderPort = Integer.parseInt(tmp[2]);
        System.out.println(socket.getLocalAddress().toString());
        System.out.println(senderIP.toString());
        System.out.println(socket.getLocalPort());
        System.out.println(senderPort);
        if(!(InetAddress.getLocalHost().getHostAddress().equals(senderIP.toString())&&(socket.getLocalPort()==senderPort))){
            return tmp[3]+" : "+tmp[4]+"\n\t"+tmp[0];
        }
        return null;
    }
}
