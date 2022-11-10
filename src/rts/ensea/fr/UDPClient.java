package rts.ensea.fr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * This class represents a client for sending udp datagram packets.
 * The client uses a socket to send packet to an host:port chosen by the user.
 * This class heavily rely on DatagramPacket and DatagramSocket classes.
 *
 * Example : UDPClient client = new UDPClient(port,address);
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see DatagramSocket
 * @see DatagramPacket
 */
public class UDPClient {
    private final int port;
    private final InetAddress address;

    /**
     * Constructs a udp client which send packets to an address:port chosen by the user.
     * @param port is a number port to which the client will send packet toward.
     * @param address is an address to which the client will send packet toward.
     */
    public UDPClient(int port, InetAddress address) {
        this.port = port;
        this.address = address;
    }

    /**
     * Send a string request over the client socket.
     * @param request is a string containing the request.
     */
    public void send(String request) throws IOException {
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buf = request.getBytes();
        DatagramPacket packet = new DatagramPacket(buf,buf.length,address,port);
        socket.send(packet);
    }
    /**
     * Start an UDP Client sending packet to an address:port provided by args[1] and args[0].
     * @param args usual main function argument.
     */
    public static void main(String[] args) {
        UDPClient client = null;
        try {
            client = new UDPClient(Integer.parseInt(args[1]),InetAddress.getByName(args[0]));
        } catch(ArrayIndexOutOfBoundsException | UnknownHostException e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String request;
        try {
            request = reader.readLine();
            assert client != null;
            client.send(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
