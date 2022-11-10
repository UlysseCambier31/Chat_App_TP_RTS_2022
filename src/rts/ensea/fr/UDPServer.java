package rts.ensea.fr;
import javax.xml.crypto.Data;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.LocalDateTime;

/**
 * This class represents a server for receiving udp datagram packets.
 * The server uses a socket which is listening on a port chosen by the user.
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
    protected final int port;
    protected DatagramSocket socket;

    /**
     * Constructs a udp server listening on the port issued by the user.
     * @param port is a number port on which the server will listen.
     */
    public UDPServer(int port) {
        this.port = port;
    }

    /**
     * Constructs an udp server listening on the default port 8080.
     */
    public UDPServer() {
        this.port = 8080;
    }

    /**
     * Start a UDP server on port provided by args[0].
     * @param args usual arguments of a main function.
     */
    public static void main(String[] args) {
        int p;
        try {
            p = Integer.parseInt(args[0]);
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("wrn: No value provided for port, using 8080");
            p=8080;
        }
        UDPServer server = new UDPServer(p);
        try {
            server.launch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Allow the user to start the UDP Server from a UDPServer object.
     * @throws IOException throws IOException.
     */
    public void launch() throws IOException {
        socket = new DatagramSocket(port);
        UDPHandler();
    }

    /**
     * Handle the reception of UDP packet on the server.
     * By default tries to decode received packet until server is closed with the stop method.
     * @throws IOException throws IOException.
     */
    public void UDPHandler() throws IOException {
        while(!socket.isClosed()) {
            DecodedPacket packet = decodePacket();
            System.out.println(packet);
        }
    }

    /**
     * Allow the user to stop the UDP Server from a UDPServer object.
     */
    public void stop(){ socket.close(); }

    /**
     * Decode the packet read on the socket.
     * @throws IOException throws IOException.
     * @return the decoded packet information.
     */
    public DecodedPacket decodePacket() throws IOException {
        int maxEncodedSize = 1024;
        byte[] buffer = new byte[maxEncodedSize];
        DatagramPacket packet= new DatagramPacket(buffer,buffer.length);
        socket.receive(packet);
        InetAddress clientAddress = packet.getAddress();
        int clientPort = packet.getPort();
        String dataReceived = new String(packet.getData(),packet.getOffset(), maxEncodedSize);
        LocalDateTime now = LocalDateTime.now();
        return new DecodedPacket(clientPort,clientAddress,dataReceived,now);
    }

    /**
     * @return UDPServer state
     */
    @Override
    public String toString() {
        return "Server is closed : "+socket.isClosed() + "/ Listening on port "+port;
    }
}
