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
        boolean userExist = false;
        for (int i=0;i<this.users.size();i++){
            if(this.users.get(i).equals(user)){
                userExist = true;
            }
        }
        if(!userExist) {this.users.add(user);};
    }

    public List<User> getUsers() {
        return users;
    }
}
