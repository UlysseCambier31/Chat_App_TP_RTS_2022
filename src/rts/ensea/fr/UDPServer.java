package rts.ensea.fr;
import javax.xml.crypto.Data;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
    private int port;

    public UDPServer(int port) {
        this.port = port;
    }

    public UDPServer() {
        this.port = 8080;
    }

    public static void main(String[] args) {
        int p = Integer.parseInt(args[0]);
        UDPServer serv = new UDPServer(p);
        try {
            serv.launch();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void launch() throws IOException {

        DatagramSocket socket = new DatagramSocket(port);
        String filename = "serv-data.txt";
        File f = new File(System.getProperty("user.dir")+filename);
        if(f.getParentFile().mkdirs()){
            System.out.println("wrn: new directory as been created.");
        }
        if(f.createNewFile()){
            System.out.println("wrn: new file as been created.");
        }
        FileReader file = new FileReader(System.getProperty("user.dir")+filename);
        BufferedReader in = new BufferedReader(file);

        while(true) {
            int maxEncodedSize = 1024;
            byte[] buffer = new byte[maxEncodedSize];
            DatagramPacket packet= new DatagramPacket(buffer,buffer.length);
            socket.receive(packet);
            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();
            String datareceived = new String(packet.getData(),packet.getOffset(), maxEncodedSize);
            System.out.println(clientAddress + " " + clientPort + " " + datareceived);
        }

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
