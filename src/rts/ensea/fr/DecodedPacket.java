package rts.ensea.fr;

import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>This class represents a decoded packet.
 * This class has been designed to be used with both UDPServer and TCPServer classes.
 * </p>
 * <p>
 * Example :</p>
 * <code>
 * DecodedPacket(decodedClientPort,decodedClientAddress,decodedDataReceived,timeOnReception);
 * </code>
 * <p>
 * Example :</p>
 * <code>
 * DecodedPacket(decodedClientPort,decodedClientAddress,decodedDataReceived,timeOnReception,connectionSocket);
 * </code>
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see UDPServer
 * @see TCPServer
 * @see Socket
 * @see InetAddress
 * @see LocalDateTime
 */
public class DecodedPacket {
    private final int port;
    private final InetAddress address;
    private final String data;
    private final LocalDateTime time;
    private final Socket connectionSocket;

    /**
     *  Constructs a decoded packet.
     * @param port decoded port number.
     * @param address decoded sender address.
     * @param data decoded data.
     * @param time time of reception of the packet.
     * @param connectionSocket connection socket on which the packet has been received.
     * @see Socket
     * @see InetAddress
     * @see LocalDateTime
     */
    public DecodedPacket(int port, InetAddress address, String data, LocalDateTime time,Socket connectionSocket) {
        this.port = port;
        this.address = address;
        this.data = data;
        this.time = time;
        this.connectionSocket = connectionSocket;
    }

    /**
     * Constructs a decoded packet.
     * @param port decoded port number.
     * @param address decoded sender address.
     * @param data decoded data.
     * @param time time of reception of the packet.
     * @see InetAddress
     * @see LocalDateTime
     */
    public DecodedPacket(int port, InetAddress address, String data, LocalDateTime time) {
        this(port,address,data,time,null);
    }

    /**
     * @return the connection socket on which the packet has been received.
     * @see Socket
     */
    public Socket getConnectionSocket() {
        return connectionSocket;
    }

    /**
     * @return the address of the sender of the packet.
     * @see InetAddress
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * @return the data of the packet.
     */
    public String getData() {
        return data;
    }

    /**
     * @return the timestamp at which the packet as been received.
     * @see LocalDateTime
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * @return the port of the sender of the packet.
     */
    public int getPort() {
        return port;
    }

    /**
     * @return a formatted display of the decoded packet information.
     * @see DateTimeFormatter
     */
    @Override
    public String toString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return timeFormatter.format(time) + " " + address+":"+port +" "+data;
    }
}
