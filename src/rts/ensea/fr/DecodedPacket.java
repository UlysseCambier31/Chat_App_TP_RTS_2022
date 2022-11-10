package rts.ensea.fr;

import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * This class represents a decoded packet.
 * This class has been designed to be used with both UDPServer and TCPServer classes.
 *
 * Example : DecodedPacket(decodedClientPort,decodedClientAddress,decodedDataReceived,timeOnReception);
 * Example : DecodedPacket(decodedClientPort,decodedClientAddress,decodedDataReceived,timeOnReception,connectionSocket);
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see UDPServer
 * @see TCPServer
 */
public class DecodedPacket {
    private final int port;
    private final InetAddress address;
    private final String data;
    private final LocalDateTime time;
    private final Socket connectionSocket;

    /**
     *
     * Constructs a decoded packet.
     *
     * @param port decoded port number.
     * @param address decoded sender address.
     * @param data decoded data.
     * @param time time of reception of the packet.
     */
    public DecodedPacket(int port, InetAddress address, String data, LocalDateTime time) {
        this.port = port;
        this.address = address;
        this.data = data;
        this.time = time;
        this.connectionSocket = null;
    }

    /**
     *
     *  Constructs a decoded packet.
     *
     * @param port decoded port number.
     * @param address decoded sender address.
     * @param data decoded data.
     * @param time time of reception of the packet.
     * @param connectionSocket connection socket on which the packet has been received.
     * @see Socket
     */
    public DecodedPacket(int port, InetAddress address, String data, LocalDateTime time,Socket connectionSocket) {
        this.port = port;
        this.address = address;
        this.data = data;
        this.time = time;
        this.connectionSocket = connectionSocket;
    }

    /**
     * @return the connection socket on which the packet has been received.
     * @see Socket
     */
    public Socket getConnectionSocket() {
        return connectionSocket;
    }

    /**
     * @return a formatted display of the decoded packet information.
     */
    @Override
    public String toString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return timeFormatter.format(time) + " " + address+":"+port +" "+data;
    }
}
