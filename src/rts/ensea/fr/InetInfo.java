package rts.ensea.fr;


import java.net.InetAddress;

public class InetInfo {
    private int port;
    private InetAddress address;

    public InetInfo(int port, InetAddress address) {
        this.port = port;
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public InetAddress getAddress() {
        return address;
    }

}
