package rts.ensea.fr;

import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ApplicationConfiguration {
    private int port;
    private InetAddress serverAddress;

    private int joinHeight;
    private int joinWidth;
    private int chatHeight;
    private int chatWidth;

    public ApplicationConfiguration(File iniFile) throws IOException {
        Ini ini = new Ini(iniFile);
        this.port = Integer.parseInt(ini.get("ServerINetInfo","port"));
        this.serverAddress = InetAddress.getByName(ini.get("ServerINetInfo","IPv4Address"));
        this.joinHeight = Integer.parseInt(ini.get("JoinWindow","height"));
        this.joinWidth = Integer.parseInt(ini.get("JoinWindow","width"));
        this.chatHeight = Integer.parseInt(ini.get("ChatWindow","height"));
        this.chatWidth = Integer.parseInt(ini.get("ChatWindow","width"));
    }

    public int getPort() {
        return port;
    }

    public InetAddress getServerAddress() {
        return serverAddress;
    }

    public int getJoinHeight() {
        return joinHeight;
    }

    public int getJoinWidth() {
        return joinWidth;
    }

    public int getChatHeight() {
        return chatHeight;
    }

    public int getChatWidth() {
        return chatWidth;
    }
}
