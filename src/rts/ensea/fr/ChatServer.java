package rts.ensea.fr;

import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ChatServer extends UDPServer{
    private Conversation conversation;

    public ChatServer( Conversation conversation) {
        super();
        this.conversation = conversation;
    }

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

    @Override
    public void UDPHandler() throws IOException {
        while(!socket.isClosed()) {
            packet = super.decodePacket();
            System.out.println(packet);
            onMessage();
        }
    }

    public void onMessage() throws IOException {
        InetInfo userNetInfo  = new InetInfo(packet.getPort(),packet.getAddress());
        String content = packet.getData();
        Message received_message = new Message(new JSONObject(content));
        User user = new User(userNetInfo,received_message.getUser().getName());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String time = timeFormatter.format(packet.getTime());
        Message message = new Message(user,content,time);
        conversation.addUser(user);
        conversation.addMessage(message);
        sendAll(message);
    }

    public void sendMessage(Message message, User user) throws IOException {
        int port = user.getNetInfo().getPort();
        InetAddress address = user.getNetInfo().getAddress();
        String serialized_data = message.serializeInJSON().toString();
        //System.out.println(serialized_data);
        super.sendPacket(port,address,serialized_data);
    }

    public void sendAll(Message message) throws IOException {
        for(int i=0;i<conversation.getUsers().size();i++){
            sendMessage(message,conversation.getUsers().get(i));
        }
    }
}
