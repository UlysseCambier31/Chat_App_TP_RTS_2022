package rts.ensea.fr;
import javax.xml.crypto.Data;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * UDP Server class.
 * Define a UDP Server Object capable of receiving UDP packets.
 */
public class UDPServer {
    private int port;
    private DatagramSocket socket;
    private boolean isUp;

    /**
     * UPDServer Constructor with port argument.
     * @param port It is the port on which the server will listen.
     */
    public UDPServer(int port) {
        this.port = port;
    }

    /**
     * UDPServer Constructor without argument.
     * Will automatically listen on 8080.
     */
    public UDPServer() {
        this.port = 8080;
    }

    public static void main(String[] args) {
        int p = Integer.parseInt(args[0]);
        UDPServer server = new UDPServer(p);
        try {
            server.launch();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * The launch method allow the user to start the UDPServer.
     * When the server is online it tries to decode received packet until it is closed with the stop method.
     * @throws IOException
     */
    public void launch() throws IOException {
        socket = new DatagramSocket(port);
        while(!socket.isClosed()) {
            decodePacket();
        }
    }

    /**
     * The stop method allow the user to stop the UDPServer.
     */
    public void stop(){ socket.close(); }

    /**
     * The method decodePacket will decode the packet read on the socket.
     * @throws IOException
     */
    public void decodePacket() throws IOException {
        int maxEncodedSize = 1024;
        byte[] buffer = new byte[maxEncodedSize];
        DatagramPacket packet= new DatagramPacket(buffer,buffer.length);
        socket.receive(packet);
        InetAddress clientAddress = packet.getAddress();
        int clientPort = packet.getPort();
        String dataReceived = new String(packet.getData(),packet.getOffset(), maxEncodedSize);
        System.out.println(clientAddress + " " + clientPort + " " + dataReceived);
    }

    @Override
    public String toString() {
        return "Server is closed : "+socket.isClosed() + "/ Listening on port "+port;
    }
}
