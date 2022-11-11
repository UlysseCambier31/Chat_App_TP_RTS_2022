package rts.ensea.fr;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

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
        byte[] buf = request.getBytes();
        output.write(buf);
    }

}
