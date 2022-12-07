package rts.ensea.fr;

import org.json.JSONObject;

import java.net.UnknownHostException;

public class Payload {
    private String command;
    private String args;
    private User user;

    public Payload(String command, String args, User user) {
        this.command = command;
        this.args = args;
        this.user = user;
    }

    public Payload(JSONObject jsonObject) throws UnknownHostException {
        this.user = new User(new JSONObject(jsonObject.getString("user")));
        this.command = jsonObject.getString("command");
        this.args = jsonObject.getString("args");
    }

    public JSONObject serializeInJSON() {
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("command",command);
        jsonobject.put("args",args);
        jsonobject.put("user",user.serializeInJSON().toString());
        return jsonobject;
    }
}
