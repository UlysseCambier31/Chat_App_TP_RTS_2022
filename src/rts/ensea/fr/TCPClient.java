package rts.ensea.fr;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class TCPClient {
    private final int port;
    private final InetAddress address;
    private final Socket socket;

    /**
     * Constructs a tcp client which send packets to an address:port chosen by the user.
     * @param port is a number port to which the client will send packet toward.
     * @param address is an address to which the client will send packet toward.
     */
    public TCPClient(int port, InetAddress address) throws IOException {
        this.port = port;
        this.address = address;
        this.socket =new Socket(address,port);
    }

    /**
     * Send a string request over the client socket.
     * @param request is a string containing the request.
     */
    public void send(String request) throws IOException {
        OutputStream output = socket.getOutputStream();
        String ending_char = "\r\n";
        byte[] buf = ByteBuffer.allocate(request.getBytes().length+ending_char.getBytes().length).put(request.getBytes()).put(ending_char.getBytes()).array();
        output.write(buf);
        output.flush();
        awaitEcho();
    }
    public void awaitEcho() throws IOException {
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String dataReceived = reader.readLine();
        System.out.println(dataReceived);
    }

    public void TCPHandler() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String request;
        while((request = reader.readLine()) != null) {
            send(request);
        }
    }

    /**
     * Start an tcp Client sending packet to an address:port provided by args[1] and args[0].
     * @param args usual main function argument.
     */
    public static void main(String[] args) {
        TCPClient client;
        try {
            client = new TCPClient(Integer.parseInt(args[1]),InetAddress.getByName(args[0]));
            client.TCPHandler();
        } catch(ArrayIndexOutOfBoundsException | IOException e) {
            e.printStackTrace();
        }
    }

}
