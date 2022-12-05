package rts.ensea.fr;

import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;

public class Command {
    String command;
    String[] args;

    public Command(String command, String[] args) {
        this.command = command;
        this.args = args;
    }
    public  Command(JSONObject jsonObject) {
        this.command = jsonObject.getString("command");
        this.args = jsonObject.getString("args").split(" ");
    }

    public JSONObject serializeInJSON() {
        JSONObject jsonobject = new JSONObject();
        if (command==null) {
            jsonobject.put("command", "");
        } else {
            jsonobject.put("command", command);
        }
        if (args==null){
            jsonobject.put("args","");
        } else {
            jsonobject.put("args", String.join(" ", args));
        }
        return jsonobject;
    }
    public void execute(ChatServer server) throws IOException {
        if (command=="get_channel") {
            server.sendConversation(args[0]);
        } else if (command=="ban") {
            server.banUser(args[0]);
        }
    }
}
