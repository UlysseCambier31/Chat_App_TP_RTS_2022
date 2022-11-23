package rts.ensea.fr;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        User user = new User(userNetInfo,packet.getAddress().toString()+":"+packet.getPort());
        String content = packet.getData();
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
        String s = "$*$";
        String data = message.getTime()+s+
                      message.getUser().getNetInfo().getAddress()+s+
                      message.getUser().getNetInfo().getPort()+s+
                      message.getUser().getName()+s+
                      message.getContent();
        super.sendPacket(port,address,data);
    }

    public void sendAll(Message message) throws IOException {
        for(int i=0;i<conversation.getUsers().size();i++){
            sendMessage(message,conversation.getUsers().get(i));
        }
    }
}
