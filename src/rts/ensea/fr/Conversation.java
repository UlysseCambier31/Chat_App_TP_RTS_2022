package rts.ensea.fr;

import org.json.JSONObject;

import java.util.List;

/**
 * <p>
 * This class represents a conversation from the application.
 * The application uses this object to store in memory the list of messages and the list of users.
 *</p>
 *<p>Example :</p>
 * <code> Conversation conversation = new Conversation(messageList, userList);</code>
 *
 * @author Ulysse Cambier, Thibaut Lefebvre
 *
 * @see JSONObject
 * @see User
 * @see Message
 * @see List
 */
public class Conversation {
    private List<Message> messages;
    private List<User> users;

    /**
     * Constructs a coversation object with a list of messages and list of users.
     * @param messages a list of message objects.
     * @param users a list of users.
     */
    public Conversation(List<Message> messages, List<User> users) {
        this.messages = messages;
        this.users = users;
    }

    /**
     * @return Return the list of all the messages from the conversation.
     * @see Message
     * @see List
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * @return Return the list of all the users from the conversation.
     * @see User
     * @see List
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Add a message object to the conversation.
     * @see Message
     */
    public void addMessage(Message message) {
        this.messages.add(message);
    }

    /**
     * Add a user object to the conversation.
     * This method check if the user is already registered in the conversation (based on the network information of the user).
     * @see User
     * @see InetInfo
     */
    public void addUser(User user) {
        boolean userIsNew = true;
        for (User value : this.users) {
            if (value.getNetInfo().getAddress().toString().equals(user.getNetInfo().getAddress().toString()) &&
                    value.getNetInfo().getPort() == user.getNetInfo().getPort()) {
                userIsNew = false;
                break;
            }
        }
        if(userIsNew) {this.users.add(user);}
    }

    /**
     * @return the user object registered in the conversation for a given username.
     * @param  username the username of the user object we search in the conversation.
     * @see User
     */
    public User getUser(String username) {
        for (int i=0;i<users.size();i++) {
            if (users.get(i).getName().equals(username)){
                return users.get(i);
            }
        }
        return null;
    }

    /**
     * @return String describing the conversation object.
     */
    @Override
    public String toString() {
        return "Conversation{" +
                "messages=" + messages +
                ", users=" + users +
                '}';
    }
}
