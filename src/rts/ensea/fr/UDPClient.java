package rts.ensea.fr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class UDPClient {
    private int port;
    private InetAddress address;

    public UDPClient(int port, InetAddress address) {
        this.port = port;
        this.address = address;
    }

    public static void main(String[] args) {
        int p;
        InetAddress a = null;
        try {
            p = Integer.parseInt(args[1]);
            a = InetAddress.getByName(args[0]);
        } catch(ArrayIndexOutOfBoundsException | UnknownHostException e) {
            System.out.println(e);
            System.out.println("wrn: No value provided as argument or wrong argument entered, using 127.0.0.1:8080");
            p=8080;
            try {
                a = InetAddress.getByName("127.0.0.1");
            } catch (UnknownHostException ex) {
                System.out.println(e);
            }
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String rqtString=null;
        try {
            rqtString = reader.readLine();
        } catch (IOException e) {
            System.out.println(e);
        }

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket( p ,a);
        } catch (SocketException e) {
            System.out.println(e);
        }

        byte[] buf = rqtString.getBytes();
        DatagramPacket request = new DatagramPacket(buf,buf.length,a,p);
        try {
            socket.send(request);
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
