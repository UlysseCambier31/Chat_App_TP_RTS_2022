package rts.ensea.fr;

import java.util.List;

public class Conversation {
    private List<Message> messages;
    private List<User> users;

    public Conversation(List<Message> messages,List<User> users) {
        this.messages = messages;
        this.users = users;
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

    public List<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "messages=" + messages +
                ", users=" + users +
                '}';
    }
}
