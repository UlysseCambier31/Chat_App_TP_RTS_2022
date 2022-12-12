package rts.ensea.fr;

import org.json.JSONObject;

import java.net.UnknownHostException;

public class Payload {
    protected String operation;
    protected String args;
    protected User user;

    public Payload(String operation, String args, User user) {
        this.operation = operation;
        this.args = args;
        this.user = user;
    }

    public Payload(JSONObject jsonObject) throws UnknownHostException {
        this.user = new User(new JSONObject(jsonObject.getString("user")));
        this.operation = jsonObject.getString("operation");
        this.args = jsonObject.getString("args");
    }

    public JSONObject serializeInJSON() {
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("operation",operation);
        jsonobject.put("args",args);
        jsonobject.put("user",user.serializeInJSON().toString());
        return jsonobject;
    }

    public String getOperation() {
        return operation;
    }


    public String getArgs() {
        return args;
    }


    public User getUser() {
        return user;
    }

}
