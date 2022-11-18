package rts.ensea.fr;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * This class represents a thread for sending tcp packets.
 * This class heavily rely on the Socket class.
 *
 * Example : thread = new ConnectionThread(socket);
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see Socket
 * @see Thread
 */
public class ConnectionThread extends java.lang.Thread {
    private Socket socket;

    /**
     * Constructs a client thread which send packets to an address:port chosen by the user.
     * @param socket the connection socket.
     */
    public ConnectionThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * Decode the packet read on the socket.
     * @throws IOException throws IOException.
     * @return the decoded packet information.
     */
    public DecodedPacket decodePacket() throws IOException {

        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        InetAddress clientAddress = socket.getInetAddress();
        int clientPort = socket.getLocalPort();
        String dataReceived = reader.readLine();
        LocalDateTime now = LocalDateTime.now();
        return new DecodedPacket(clientPort,clientAddress,dataReceived,now,socket);
    }

    /**
     * Answer "echo\r\n" to the client on the socket.
     * @param connectionSocket the connection socket.
     * @throws IOException throws IOException.
     */
    public void answer(Socket connectionSocket) throws IOException {
        OutputStream output = connectionSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, false);
        writer.print("echo\r\n");
        writer.flush();
    }

    /**
     * Answer a string to the client on the socket.
     * @param connectionSocket the connection socket.
     * @param answer the string to be answered by the server to the client.
     * @throws IOException throws IOException.
     */
    public void answer(Socket connectionSocket,String answer) throws IOException {
        OutputStream output = connectionSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, false);
        writer.print(answer+"\r\n");
        writer.flush();
    }

    /**
     * Override the run() method from java.lang.Thread in order to Decode packet, print them on screen and then answer to the Client.
     */
    @Override
    public void run() {
        DecodedPacket packet = null;
        try {
            packet = decodePacket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(packet);
        try {
            assert packet != null;
            answer(packet.getConnectionSocket());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
