package rts.ensea.fr;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
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
        String dataReceived = reader.readLine();
        System.out.println(dataReceived);
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

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String request;
        try {
                while((request = reader.readLine()) != null) {
                    assert client != null;
                    client.send(request);
                }
        } catch (IOException e) {
               e.printStackTrace();
        }

    }

}
