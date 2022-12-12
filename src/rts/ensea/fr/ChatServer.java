package rts.ensea.fr;

import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.security.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * <p>
 * This class represents a server for the chat application.
 * The class extends from UDPServer.
 *</p>
 *<p>Example :</p>
 * <code> ChatServer server = new ChatServer(conversation);</code>
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see Conversation
 * @see Message
 * @see User
 */
public class ChatServer extends UDPServer{
    private Conversation conversation;

    /**
     * Constructs a ChatServer from a conversation.
     * @param conversation a conversation.
     * @see Conversation
     */
    public ChatServer( Conversation conversation)  {
        super();
        this.conversation = conversation;
    }

    /**
     * Handle reception of UDP packets.
     * @throws IOException Throws an IOException.
     * @see java.net.DatagramSocket
     * @see DatagramPacket
     * @see UDPServer
     */
    @Override
    public void UDPHandler() throws IOException {
        while(!socket.isClosed()) {
            packet = super.decodePacket();
            System.out.println(packet);
            onMessage();
        }
    }

    /**
     * Called on message reception from the server.
     * Enforce the rules of the applicative protocol.
     * Handle the answer of the server to different types of payload received.
     * @throws IOException Throws an IOException.
     * @see java.net.DatagramSocket
     * @see DatagramPacket
     * @see UDPServer
     */
    public void onMessage() throws IOException {
        InetInfo userNetInfo  = new InetInfo(packet.getPort(),packet.getAddress());
        String content = packet.getData();
        Payload payload_received = new Payload(new JSONObject(content));
        if (payload_received.getOperation().equals("send")){ // Applicative protocol send operation.
            Message received_message = new Message(new JSONObject(payload_received.getArgs()));
            User user = new User(userNetInfo, received_message.getUser().getName());
            Message message = new Message(user, received_message.getContent(), received_message.getTime());
            sendAll(message,user); // Send to all user the message from the sender.
        }else if (payload_received.getOperation().equals("connect")) { // Applicative protocol connect operation.
            User user = new User(userNetInfo,payload_received.getUser().getName());
            conversation.addUser(user);
            sendConversation(user);
            InetInfo serverNetInfo = new InetInfo(socket.getLocalPort(),socket.getLocalAddress());
            User server = new User(serverNetInfo,"App"); // create a user for the Application / server
            Message message = new Message(server,"User @"+user.getName()+" has connected to the server!",payload_received.getArgs());
            sendAll(message,server); // Send to all user welcoming message from the app.
        }
    }

    /**
     * Send to all users of the conversation a message and add this message to the conversation.
     * @param message a message object.
     * @param user a user object.
     * @throws IOException Throws an IOException.
     * @see Message
     * @see User
     */
    public void sendAll(Message message, User user) throws IOException {
        Payload payload = new Payload("",message.serializeInJSON().toString(),user);
        conversation.addMessage(message);
        sendAll(payload);
    }

    /**
     * Send to all users of the conversation a payload.
     * @param payload a payload object.
     * @throws IOException Throws an IOException.
     * @see Payload
     */
    public void sendAll(Payload payload) throws IOException {
        for(int i=0;i<conversation.getUsers().size();i++){
            sendPayload(payload,conversation.getUsers().get(i));
        }
    }

    /**
     * Send to a given user the whole conversation
     * @param  user a user object.
     * @throws IOException Throws an IOException.
     * @see User
     * @see Payload
     */
    public void sendConversation(User user) throws IOException {
        for(int i=0;i<conversation.getMessages().size();i++){
            Message message = conversation.getMessages().get(i);
            Payload payload = new Payload("",message.serializeInJSON().toString(),message.getUser());
            sendPayload(payload,user);
        }
    }

    /**
     * Send a payload to an user.
     * @param  payload a payload object.
     * @param  user a user object.
     * @throws IOException Throws an IOException.
     * @see User
     * @see Payload
     */
    public void sendPayload(Payload payload, User user) throws IOException {
        int port = user.getNetInfo().getPort();
        InetAddress address = user.getNetInfo().getAddress();
        String serialized_data = payload.serializeInJSON().toString();
        super.sendPacket(port,address,serialized_data);
    }

    /**
     * Start a UDP chat server on port 8080.
     */
    public static void main(String[] args) {
        List<User> users = new ArrayList<>() ;
        List<Message> messages = new ArrayList<>();
        Conversation conversation = new Conversation(messages,users);
        ChatServer server = new ChatServer(conversation);
        try {
            server.launch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
