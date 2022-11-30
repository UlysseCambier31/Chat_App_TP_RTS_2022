package rts.ensea.fr;

import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class User {
    private InetInfo netInfo;
    private String name;

    public User(InetInfo netInfo, String name) {
        this.netInfo = netInfo;
        this.name = name;
    }
    public User(JSONObject jsonObject) throws UnknownHostException {
        this.netInfo = new InetInfo(new JSONObject(jsonObject.getString("InetInfo")));
        this.name = jsonObject.getString("name");
    }

    @Override
    public String toString() {
        return " User{" +
                "netInfo=" + netInfo +
                ", name='" + name + '\'' +
                '}';
    }

    public JSONObject serializeInJSON() {
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("InetInfo",netInfo.serializeInJSON().toString());
        jsonobject.put("name",name);
        return jsonobject;
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
