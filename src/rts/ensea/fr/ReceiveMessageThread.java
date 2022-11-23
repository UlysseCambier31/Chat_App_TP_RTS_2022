package rts.ensea.fr;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

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
            System.out.println(message);
        }
    }

    public String awaitMessage() throws IOException {
        int maxEncodedSize = 1024;
        byte[] buffer = new byte[maxEncodedSize];
        DatagramPacket packet= new DatagramPacket(buffer,buffer.length);
        socket.receive(packet);
        return new String(packet.getData(),packet.getOffset(), maxEncodedSize);
    }
}
