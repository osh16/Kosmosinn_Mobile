package is.hi.hbv501g.kosmosinn.Kosmosinn.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // hvert chat getur haft marga users
    @OneToMany
    private List<User> users = new ArrayList<User>();

    // hvert chat getur haft morg skilabod
    @OneToMany
    private List<Message> messages = new ArrayList<Message>();

    public Chat() {

    }

    public Chat(List<User> users, List<Message> messages) {
        this.users = users;
        this.messages = messages;
    }

    public List<User> getParticipants() {
        return users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setParticipants(List<User> users) {
        this.users = users;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
