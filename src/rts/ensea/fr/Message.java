package rts.ensea.fr;

import java.io.File;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

/**
 * <p>
 * This class represents a message from the conversation.
 * The application uses this object to store in memory the user, the content and the timestamp of the message.
 * This class comes with it's own JSON serialization system.
 *</p>
 *<p>Example :</p>
 * <code> Message message = new Message(user,"Hello World !",timestring);</code>
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see JSONObject
 * @see User
 */
public class Message {
    private User user;
    private String content;
    private String time;

    /**
     * Constructs a message object with it's own user, content and timestamp.
     * @param user the user object of the sender of the message.
     * @param content the content of the message.
     * @param time the timestamp of the message.
     * @see User
     */
    public Message(User user, String content, String time) {
        this.user = user;
        this.content = content;
        this.time = time;
    }

    /**
     * Constructs a message object from a jsonObject.
     * @param jsonObject a JSONObject containing the InetInfo and the name of an user.
     * @throws UnknownHostException throws an UnkownHostException.
     * @see User
     * @see JSONObject
     */
    public  Message(JSONObject jsonObject) throws UnknownHostException {
        this(
                new User(new JSONObject(jsonObject.getString("user"))),
                jsonObject.getString("content"),
                jsonObject.getString("time")
        );
    }

    /**
     * @return Return the user object describing the sender of the message.
     * @see User
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user object for the sender of the message.
     * @param user an user object describing the sender of the message.
     * @see User
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return Return the content of the message.
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the content of the message.
     * @param content the content of the message.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return Return the timestamp of the message.
     */
    public String getTime() {
        return time;
    }

    /**
     * Set the timetsamp of the message.
     * @param time the timestamp of the message.
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return Return the JSON object in which the message object as been serialized.
     */
    public JSONObject serializeInJSON() {
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("user",user.serializeInJSON().toString());
        jsonobject.put("content",content);
        jsonobject.put("time",time);
        return jsonobject;
    }

    /**
     * @return String describing the message object.
     */
    @Override
    public String toString() {
        return "\n Message{" +
                "user=" + user +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

}
