package rts.ensea.fr;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.time.format.DateTimeFormatter;

public class ChatServer extends UDPServer{
    private Conversation conversation;

    public ChatServer( Conversation conversation) {
        super(8080);
        this.conversation = conversation;
    }

    public static void main(String[] args) {

        Conversation conversation = new Conversation(null,null);
        ChatServer server = new ChatServer(conversation);
        try {
            server.launch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void UDPHandler() throws IOException {
        super.UDPHandler();
        onMessage();
    }

    public void onMessage() throws IOException {
        InetInfo userNetInfo  = new InetInfo(packet.getPort(),packet.getAddress());
        User user = new User(userNetInfo,packet.getAddress().toString());
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
        String data = message.getUser().getName()+" : "+message.getContent() + "\n\t" + message.getTime();
        super.sendPacket(port,address,data);
    }

    public void sendAll(Message message) throws IOException {
        for(int i=0;i<conversation.getUsers().size();i++){
            sendMessage(message,conversation.getUsers().get(i));
        }
    }
}
