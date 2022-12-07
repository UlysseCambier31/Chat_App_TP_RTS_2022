package rts.ensea.fr;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;

/**
 * This class represents a server for receiving tcp packets.
 * The server uses a socket which is listening on a port chosen by the user.
 * This class heavily rely on ServerSocket and Socket classes.
 *
 * Example : TCPServer server = new TCPServer(8080); Which is equivalent to TCPServer server = new TCPServer(); as default issued port will be 8080.
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see ServerSocket
 * @see Socket
 */
public class TCPServer{
    protected final int port;
    private ServerSocket socket;

    /**
     * Constructs a tcp server listening on the port issued by the user.
     * @param port is a number port on which the server will listen.
     */
    public TCPServer(int port) {
        this.port = port;
    }

    /**
     * Constructs a tcp server listening on the port 8080.
     */
    public TCPServer() {
        this(8080);
    }

    /**
     * Start a tcp server on port provided by args[0].
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
        TCPServer server = new TCPServer(p);
        try {
            server.launch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void launch() throws IOException {
        socket = new ServerSocket(port);
        TCPHandler();
    }

    /**
     * Handle the reception of TCP packet on the server.
     * By default tries to decode received packet until server is closed with the stop method.
     * @throws IOException throws IOException.
     */
    public void TCPHandler() throws IOException {
        while(!socket.isClosed()){
            Socket connectionSocket = socket.accept();
            ConnectionThread thread = new ConnectionThread(connectionSocket);
            thread.start();
        }
    }

    /**
     * Allow the user to stop the TCP Server from a TCPServer object.
     */
    public void stop() throws IOException { socket.close(); }

    /**
     * @return TCPServer state
     */
    @Override
    public String toString() {
        return "Server is closed : "+socket.isClosed() + "/ Listening on port "+port;
    }
}
