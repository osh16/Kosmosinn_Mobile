package is.hi.hbv501g.kosmosinn.Kosmosinn.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // hvert chat getur haft marga participants
    @OneToMany
    private List<User> participants = new ArrayList<User>();

    // hvert chat getur haft morg skilabod
    @OneToMany
    private List<Message> messages = new ArrayList<Message>();

    public Chat(List<User> participants, List<Message> messages) {
        this.participants = participants;
        this.messages = messages;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
