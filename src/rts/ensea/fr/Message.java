package rts.ensea.fr;

import java.io.File;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

public class Message {
    private User user;
    private String content;
    private String time;
    private List<String> reactions;
    private File embed;

    public Message(User user, String content, String time) {
        this.user = user;
        this.content = content;
        this.time = time;
        this.reactions = null;
        this.embed = null;
    }
    public  Message(JSONObject jsonObject) throws UnknownHostException {
        this.user = new User(new JSONObject(jsonObject.getString("user")));
        this.content = jsonObject.getString("content");
        this.time = jsonObject.getString("time");
        this.reactions = null;
        this.embed = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "\n Message{" +
                "user=" + user +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", reactions=" + reactions +
                ", embed=" + embed +
                '}';
    }

    public JSONObject serializeInJSON() {
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("user",user.serializeInJSON().toString());
        jsonobject.put("content",content);
        jsonobject.put("time",time);
        return jsonobject;
    }


}
