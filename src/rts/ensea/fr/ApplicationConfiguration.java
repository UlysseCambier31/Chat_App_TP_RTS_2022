package rts.ensea.fr;

import javafx.application.Application;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>
 * This class represents the application configuration.
 * This class heavily rely on org.ini4j.Ini
 *</p>
 *<p>Example :</p>
 *<code> ApplicationConfiguration appConfig = new ApplicationConfiguration(configFile);</code>
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see Ini
 */

public class ApplicationConfiguration {
    private int port;
    private InetAddress serverAddress;

    private int joinHeight;
    private int joinWidth;
    private int chatHeight;
    private int chatWidth;

    /**
     * Constructs an ApplicationConfiguration object from an .ini file.
     * @param iniFile a .ini file containing configuration data.
     * @see Ini
     * @see File
     */
    public ApplicationConfiguration(File iniFile) throws IOException {
        Ini ini = new Ini(iniFile);
        this.port = Integer.parseInt(ini.get("ServerINetInfo","port"));
        this.serverAddress = InetAddress.getByName(ini.get("ServerINetInfo","IPv4Address"));
        this.joinHeight = Integer.parseInt(ini.get("JoinWindow","height"));
        this.joinWidth = Integer.parseInt(ini.get("JoinWindow","width"));
        this.chatHeight = Integer.parseInt(ini.get("ChatWindow","height"));
        this.chatWidth = Integer.parseInt(ini.get("ChatWindow","width"));
    }

    /**
     * @return Return the port configuration.
     */
    public int getPort() {
        return port;
    }

    /**
     * @return Return the server address configuration.
     * @see InetAddress
     */
    public InetAddress getServerAddress() {
        return serverAddress;
    }

    /**
     * @return Return the Join Windows Height.
     */
    public int getJoinHeight() {
        return joinHeight;
    }

    /**
     * @return Return the Join Windows Width.
     */
    public int getJoinWidth() {
        return joinWidth;
    }

    /**
     * @return Return the Chat Windows Height.
     */
    public int getChatHeight() {
        return chatHeight;
    }

    /**
     * @return Return the Chat Windows Width.
     */
    public int getChatWidth() {
        return chatWidth;
    }
}
