package rts.ensea.fr;


import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>
 * This class represents network information about an user.
 * The application uses this object to store in memory the port and InetAddress of an user.
 * This class comes with it's own JSON serialization system.
 *</p>
 *<p>Example :</p>
 * <code> InetInfo netInfo = new InetInfo(8080,address);</code>
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see JSONObject
 * @see User
 * @see InetAddress
 */

public class InetInfo {
    private int port;
    private InetAddress address;

    /**
     * Constructs an InetInfo object with it's own port and address.
     * @param port a port.
     * @param address an InetAddress.
     * @see InetAddress
     */
    public InetInfo(int port, InetAddress address) {
        this.port = port;
        this.address = address;
    }

    /**
     * Constructs an InetInfo object from a jsonObject.
     * @param jsonObject a JSONObject containing the port and the address of the InetInfo object.
     * @throws UnknownHostException throws an UnkownHostException.
     * @see InetAddress
     * @see JSONObject
     */
    public InetInfo(JSONObject jsonObject) throws UnknownHostException {
        this(
                Integer.parseInt(jsonObject.getString("port")),
                InetAddress.getByName(jsonObject.getString("address"))
        );
    }

    /**
     * @return Return the port of the InetInfo.
     */
    public int getPort() {
        return port;
    }

    /**
     * @return Return the address of the InetInfo.
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * @return Return the JSON object in which the InetInfo object as been serialized.
     */
    public JSONObject serializeInJSON() {
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("port",Integer.toString(port));
        jsonobject.put("address",address.getHostAddress());
        return jsonobject;
    }

    /**
     * @return String describing the User object.
     */
    @Override
    public String toString() {
        return "InetInfo{" +
                "port=" + port +
                ", address=" + address +
                '}';
    }

}
