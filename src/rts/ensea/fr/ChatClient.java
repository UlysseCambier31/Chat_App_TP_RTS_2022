package rts.ensea.fr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ChatClient extends UDPClient {

    public ChatClient(int port, InetAddress address) throws SocketException {
        super(port, address);
    }

    public static void main(String[] args) {
        ChatClient client = null;
        try {
            client = new ChatClient(Integer.parseInt(args[1]),InetAddress.getByName(args[0]));
        } catch(ArrayIndexOutOfBoundsException | UnknownHostException | SocketException e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        String request;
        try {
            request = reader.readLine();
            assert client != null;
            client.send(request);
            String answer = client.awaitAnswer();
            System.out.println(answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String awaitAnswer() throws IOException {
        int maxEncodedSize = 1024;
        byte[] buffer = new byte[maxEncodedSize];
        DatagramPacket packet= new DatagramPacket(buffer,buffer.length);
        socket.receive(packet);
        return new String(packet.getData(),packet.getOffset(), maxEncodedSize);
    }
}
