package rts.ensea.fr;


import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetInfo {
    private int port;
    private InetAddress address;

    public InetInfo(int port, InetAddress address) {
        this.port = port;
        this.address = address;
    }

    public InetInfo(JSONObject jsonObject) throws UnknownHostException {
        this.port = Integer.parseInt(jsonObject.getString("port"));
        this.address = InetAddress.getByName(jsonObject.getString("address"));
    }

    public int getPort() {
        return port;
    }

    public InetAddress getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "InetInfo{" +
                "port=" + port +
                ", address=" + address +
                '}';
    }

    public JSONObject serializeInJSON() {
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("port",Integer.toString(port));
        jsonobject.put("address",address.getHostAddress());
        return jsonobject;
    }

}
