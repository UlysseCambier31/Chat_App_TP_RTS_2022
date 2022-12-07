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
        Payload payload_received = new Payload(new JSONObject(content));
        if (payload_received.getOperation().equals("send")){
            Message received_message = new Message(new JSONObject(payload_received.getArgs()));
            User user = new User(userNetInfo, received_message.getUser().getName());
            conversation.addUser(user);
            Message message = new Message(user, received_message.getContent(), received_message.getTime());
            Payload payload = new Payload("",message.serializeInJSON().toString(),user);// Why not user = server ?
            conversation.addMessage(message);
            sendAll(payload);
        }
    }

    public void sendPayload(Payload payload, User user) throws IOException {
        int port = user.getNetInfo().getPort();
        InetAddress address = user.getNetInfo().getAddress();
        String serialized_data = payload.serializeInJSON().toString();
        //System.out.println(serialized_data);
        super.sendPacket(port,address,serialized_data);
    }

    public void sendAll(Payload payload) throws IOException {
        for(int i=0;i<conversation.getUsers().size();i++){
            sendPayload(payload,conversation.getUsers().get(i));
        }
    }

    public void banUser(String username){
        conversation.addBannedUser(conversation.getUser(username));
    }
}
