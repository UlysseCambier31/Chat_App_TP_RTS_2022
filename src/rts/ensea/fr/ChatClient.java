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

/**
 * <p>
 * This class represents a client for the chat application.
 * The class extends from UDPClient.
 *</p>
 *<p>Example :</p>
 * <code> ChatClient client = new ChatClient(port,serverAddress);</code>
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see UDPClient
 * @see InetAddress
 * @see DatagramPacket
 * @see DatagramSocket
 */
public class ChatClient extends UDPClient {

    /**
     * Constructs a ChatClient object with the port and the address of the ChatServer.
     * @param port the port of the server.
     * @param address the address of the server.
     * @throws SocketException Throws a Socket Exception.
     * @see InetAddress
     */
    public ChatClient(int port, InetAddress address) throws SocketException {
        super(port, address);
    }

    /**
     * @return socket of the ChatClient.
     * @see DatagramSocket
     */
    public DatagramSocket getSocket() {
        return socket;
    }

    /**
     * Send content to the sever.
     * @param  content the content to be sent.
     * @param username the username of the user.
     * @throws  IOException Throws an IOException.
     */
    public void send(String content, String username) throws IOException {
        InetInfo netInfo = new InetInfo(socket.getLocalPort(),socket.getLocalAddress());
        User user = new User(netInfo,username);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        Message message = new Message(user, content, timeFormatter.format(localDateTime));
        sendMessage(message);
    }

    /**
     * Send a Message to the sever using the applicative protocol defined in Payload.
     * @param  message the message object to be sent.
     * @throws  IOException Throws an IOException.
     * @see Message
     * @see Payload
     */
    public void sendMessage(Message message) throws IOException {
        String serialized_message = message.serializeInJSON().toString();
        Payload payload = new Payload("send",serialized_message,message.getUser());
        sendPayload(payload);
    }

    /**
     * Send a Payload to the sever.
     * @param  payload the payload object to be sent.
     * @throws  IOException Throws an IOException.
     * @see Payload
     */
    public  void sendPayload(Payload payload) throws IOException {
        String serialized_payload = payload.serializeInJSON().toString();
        super.send(serialized_payload);
    }

    /**
     * connect with the server using the applicative protocol defined by Payload.
     * @param  username the username of the user.
     * @throws  IOException Throws an IOException.
     * @see Payload
     */
    public void connect(String username) throws IOException {
        InetInfo netInfo = new InetInfo(socket.getLocalPort(),socket.getLocalAddress());
        User user = new User(netInfo, username);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        Payload payload = new Payload("connect",timeFormatter.format(localDateTime),user);
        String serialized_payload = payload.serializeInJSON().toString();
        super.send(serialized_payload);
    }

    /**
     * Start a ChatClient sending packet to an address:port provided by args[1] and args[0].
     * @param args usual main function argument.
     */
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

        assert client != null;
        ReceiveMessageThread receiveMessageHandlerThread = new ReceiveMessageThread(client.getSocket(),username);
        receiveMessageHandlerThread.start();

        try {
            client.send("Hi it's "+username+" ! I've just connected to the server !", username);
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
}
