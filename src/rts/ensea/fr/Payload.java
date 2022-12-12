package rts.ensea.fr;

import org.json.JSONObject;

import java.net.UnknownHostException;

/**
 * <p>This class represents a payload to be send in a packet.
 * The application uses this object to allow the server to use a layer of applicative protocol.
 * The class as an operation label, arguments for the operation and a user object containing the data of the user sending the payload.
 * This class comes with it's own JSON serialization system.
 * </p>
 * <p> Example :</p>
 * <code>
 * Payload payload("send",serializedMessage,user);
 * </code>
 * <p>The current Applicative protocol utilizing this class allow the user to send 3 types of payloads:</p>
 * <ul>
 *     <li>operation="" the exchanged payload is a message from server to user</li>
 *     <li>operation="send" the exchanged payload is a message from user to server that needed to be send to each users of the conversation.</li>
 *     <li>operation="connect" the exchanged payload is a connection request to register the user in the conversation and to make the server send the entire conversation to the new user. The payload contain the time at which the request as been made by the user.</li>
 * </ul>
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see JSONObject
 * @see User
 */
public class Payload {
    protected String operation;
    protected String args;
    protected User user;

    /**
     * Constructs a payload object with it's operation, arguments and user object.
     * @param operation an operation {"","send","connect"}.
     * @param args the arguments of the operation.
     * @param user an user of the application.
     * @see User
     */
    public Payload(String operation, String args, User user) {
        this.operation = operation;
        this.args = args;
        this.user = user;
    }

    /**
     * Constructs a payload object from a jsonObject.
     * @param jsonObject a JSONObject containing the operation, arguments and user of the payload.
     * @throws UnknownHostException Throws an UnknownHostException.
     * @see JSONObject
     * @see User
     */
    public Payload(JSONObject jsonObject) throws UnknownHostException {
        this(
                jsonObject.getString("operation"),
                jsonObject.getString("args"),
                new User(new JSONObject(jsonObject.getString("user")))
        );
    }

    /**
     * @return Return the operation of the payload.
     */
    public String getOperation() {
        return operation;
    }

    /**
     * @return Return the arguments of the payload.
     */
    public String getArgs() {
        return args;
    }

    /**
     * @return Return the user object of the Payload.
     * @see User
     */
    public User getUser() {
        return user;
    }

    /**
     * @return Return the JSON object in which the payload object as been serialized.
     */
    public JSONObject serializeInJSON() {
        JSONObject jsonobject = new JSONObject();
        jsonobject.put("operation",operation);
        jsonobject.put("args",args);
        jsonobject.put("user",user.serializeInJSON().toString());
        return jsonobject;
    }

}
