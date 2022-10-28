package rts.ensea.fr;
import javax.xml.crypto.Data;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * This class represents a server for receiving udp datagram packets.
 * The server uses a socket which is listening ona a port chosen by the user.
 * This class heavily rely on DatagramPacket and DatagramSocket classes.
 *
 * Example : UDPServer server = new UDPServer(8080); Which is equivalent to UDPServer server = new UDPServer(); as default issued port will be 8080.
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see DatagramSocket
 * @see DatagramPacket
 */
public class UDPServer {
    private int port;
    private DatagramSocket socket;
    private boolean isUp;

    /**
     * Constructs a udp server listening on the port issued by the user.
     * @param port is a number port on which the server will listen.
     */
    public UDPServer(int port) {
        this.port = port;
    }

    /**
     * Constructs a udp server listening on the default port 8080.
     */
    public UDPServer() {
        this.port = 8080;
    }

    /**
     * Start an UDP server on port provided by args[0].
     * @param args
     */
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
     * Allow the user to start the UDP Server from a UDPServer object.
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
     * Allow the user to stop the UDP Server from a UDPServer object.
     */
    public void stop(){ socket.close(); }

    /**
     * Decode the packet read on the socket.
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

    /**
     * @return UDPServer state
     */
    @Override
    public String toString() {
        return "Server is closed : "+socket.isClosed() + "/ Listening on port "+port;
    }
}
