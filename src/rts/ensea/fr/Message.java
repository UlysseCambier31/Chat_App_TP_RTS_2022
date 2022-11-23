package rts.ensea.fr;

import java.io.File;
import java.util.List;

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
}
