package rts.ensea.fr;

import java.util.List;

public class Conversation {
    private List<Message> messages;
    private List<User> users;
    private List<User> bannedusers;

    public Conversation(List<Message> messages, List<User> users) {
        this.messages = messages;
        this.users = users;
        this.bannedusers= null;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Message getLastMessage() {
        return messages.get(messages.size()-1);
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

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

    public void addBannedUser(User user) {
        boolean userIsNew = true;
        for (User value : this.bannedusers) {
            if (value.getNetInfo().getAddress().toString().equals(user.getNetInfo().getAddress().toString()) &&
                    value.getNetInfo().getPort() == user.getNetInfo().getPort()) {
                userIsNew = false;
                break;
            }
        }
        if(userIsNew) {this.bannedusers.add(user);}
    }

    public List<User> getBannedusers() {
        return bannedusers;
    }

    public boolean isBanned(User user) {
        boolean userIsBanned = true;
        for (User value : this.bannedusers) {
            if (value.getNetInfo().getAddress().toString().equals(user.getNetInfo().getAddress().toString()) &&
                    value.getNetInfo().getPort() == user.getNetInfo().getPort()) {
                userIsBanned = false;
                break;
            }
        }
        return userIsBanned;
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUser(String username) {
        for (int i=0;i<users.size();i++) {
            if (users.get(i).getName().equals(username)){
                return users.get(i);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "messages=" + messages +
                ", users=" + users +
                '}';
    }
}
