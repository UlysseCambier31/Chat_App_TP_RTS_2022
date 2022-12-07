package rts.ensea.fr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

public class ChatClient extends UDPClient {

    public ChatClient(int port, InetAddress address) throws SocketException {
        super(port, address);
    }

    public void send(String content, String username) throws IOException {
        InetInfo netInfo = new InetInfo(socket.getLocalPort(),socket.getLocalAddress());
        User user = new User(netInfo,username);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        Message message = new Message(user, content, timeFormatter.format(localDateTime));
        String serialized_message = message.serializeInJSON().toString();
        Payload payload = new Payload("send",serialized_message,user);
        String serialized_payload = payload.serializeInJSON().toString();
        super.send(serialized_payload);
    }

    public void connect(String username) throws IOException {
        InetInfo netInfo = new InetInfo(socket.getLocalPort(),socket.getLocalAddress());
        User user = new User(netInfo,username);
        Payload payload = new Payload("connect","",user);
        String serialized_payload = payload.serializeInJSON().toString();
        super.send(serialized_payload);
    }

    public static void main(String[] args) {
        ChatClient client = null;
        try {
            client = new ChatClient(Integer.parseInt(args[1]),InetAddress.getByName(args[0]));
        } catch(ArrayIndexOutOfBoundsException | UnknownHostException | SocketException e) {
            e.printStackTrace();
        }

        System.out.print("Entrez un nom d'utilisateur \n @");
        BufferedReader usernamereader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        String username = null;
        try {
            username = usernamereader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ReceiveMessageThread receiveMessageHandlerThread = new ReceiveMessageThread(client.getSocket(),username);
        receiveMessageHandlerThread.start();

        try {
            client.send("\\get_channel");
            client.send("Salut c'est "+username+" ! Je viens de rentrer dans le serveur !", username);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        String message;
        try {
            assert client != null;
            while(true) {
                message = reader.readLine();
                client.send(message, username);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public DatagramSocket getSocket() {
        return socket;
    }
}
