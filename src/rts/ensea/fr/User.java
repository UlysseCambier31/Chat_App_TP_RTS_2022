package rts.ensea.fr;

import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>
 * This class represents an application user.
 * The application uses this object to store in memory the InetInfo of the user as well as it's username.
 * This class comes with it's own JSON serialization system.
 *</p>
 *<p>Example :</p>
 * <code> User user = new User(netInfo, "username");</code>
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see JSONObject
 * @see InetInfo
 */
public class User {
    private InetInfo netInfo;
    private String name;

    /**
     * Constructs an user object with it's own InetInfo and name.
     * @param netInfo a InetInfo object that describe the network information of an user.
     * @param name an username.
     */
    public User(InetInfo netInfo, String name) {
        this.netInfo = netInfo;
        this.name = name;
    }

    /**
     * Constructs an user object from a jsonObject.
     * @param jsonObject a JSONObject containing the InetInfo and the name of an user.
     * @throws UnknownHostException throws an UnkownHostException.
     * @see InetInfo
     * @see JSONObject
     */
    public User(JSONObject jsonObject) throws UnknownHostException {
        this(
                new InetInfo(new JSONObject(jsonObject.getString("InetInfo"))),
                jsonObject.getString("name")
        );
    }

    /**
     * @return Return the InetInfo object describing the user network information.
     * @see InetInfo
     */
    public InetInfo getNetInfo() {
        return netInfo;
    }

    /**
     * @return Return the name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of an user object.
     * @param name the name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Return the JSON object in which the user object as been serialized.
     */
    public JSONObject serializeInJSON() {
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("InetInfo",netInfo.serializeInJSON().toString());
        jsonobject.put("name",name);
        return jsonobject;
    }

    /**
     * @return String describing the User object.
     */
    @Override
    public String toString() {
        return " User{" +
                "netInfo=" + netInfo +
                ", name='" + name + '\'' +
                '}';
    }

}
