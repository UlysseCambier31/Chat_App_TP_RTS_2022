package rts.ensea.fr;

import java.net.InetAddress;

public class User {
    private InetInfo netInfo;
    private String name;

    public User(InetInfo netInfo, String name) {
        this.netInfo = netInfo;
        this.name = name;
    }

    @Override
    public String toString() {
        return " User{" +
                "netInfo=" + netInfo +
                ", name='" + name + '\'' +
                '}';
    }

    public InetInfo getNetInfo() {
        return netInfo;
    }

    public void setNetInfo(InetInfo netInfo) {
        this.netInfo = netInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
