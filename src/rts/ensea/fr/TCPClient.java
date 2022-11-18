package rts.ensea.fr;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * This class represents a client for sending tcp packets.
 * The client uses a socket to send packet to an host:port chosen by the user.
 * This class heavily rely on the Socket class.
 *
 * Example : client = new TCPClient(port,ipAddress);
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see Socket
 */
public class TCPClient {
    private final int port;
    private final InetAddress address;

    /**
     * Constructs a tcp client which send packets to an address:port chosen by the user.
     * @param port is a number port to which the client will send packet toward.
     * @param address is an address to which the client will send packet toward.
     */
    public TCPClient(int port, InetAddress address) {
        this.port = port;
        this.address = address;
    }

    /**
     * Send a string request over the client socket.
     * @param request is a string containing the request.
     * @return the connection socket.
     * @throws IOException throws IOException.
     */
    public Socket send(String request) throws IOException {
        Socket socket = new Socket(address,port);
        OutputStream output = socket.getOutputStream();
        String ending_char = "\r\n";
        byte[] buf = ByteBuffer.allocate(request.getBytes().length+ending_char.getBytes().length).put(request.getBytes()).put(ending_char.getBytes()).array();
        output.write(buf);
        output.flush();
        return socket;
    }

    /**
     * Await for an "echo\r\n" from the server.
     * @param socket the connection socket
     * @throws IOException throws IOException.
     */
    public void awaitEcho(Socket socket) throws IOException {
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String dataReceived = reader.readLine();
        System.out.println(dataReceived);
    }

    /**
     * Handle the sending of TCP packet on the server.
     * By default tries to send packets until the connection is closed with Ctrl+D.
     * @param reader the input BufferedReader.
     * @throws IOException throws IOException.
     */
    public void TCPHandler(BufferedReader reader) throws IOException {
        String request;
        while((request = reader.readLine()) != null) {
            Socket socket = send(request);
            awaitEcho(socket);
        }
    }

    /**
     * Start an tcp Client sending packet to an address:port provided by args[1] and args[0].
     * @param args usual main function argument.
     */
    public static void main(String[] args) {
        TCPClient client = null;
        try {
            client = new TCPClient(Integer.parseInt(args[1]),InetAddress.getByName(args[0]));
        } catch(ArrayIndexOutOfBoundsException | UnknownHostException e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        try {
            assert client != null;
            client.TCPHandler(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}