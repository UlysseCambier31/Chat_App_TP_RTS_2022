package rts.ensea.fr;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * This class represents a decoded packet.
 * This class has been designed to be used with the UDPServer class.
 *
 * Example : DecodedPacket(decodedClientPort,decodedClientAddress,decodedDataReceived,timeOnReception);
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see UDPServer
 */
public class DecodedPacket {
    private int port;
    private InetAddress address;
    private String data;
    private LocalDateTime time;

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
