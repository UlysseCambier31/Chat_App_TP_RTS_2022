package rts.ensea.fr;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

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
     */
    public void send(String request) throws IOException {
        Socket socket = new Socket(address,port);
        OutputStream output = socket.getOutputStream();
        String ending_char = "\r\n";
        byte[] buf = ByteBuffer.allocate(request.getBytes().length+ending_char.getBytes().length).put(request.getBytes()).put(ending_char.getBytes()).array();
        output.write(buf);
        output.flush();
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String dataReceived = String.valueOf(reader);
        System.out.println(dataReceived);
    }

}
