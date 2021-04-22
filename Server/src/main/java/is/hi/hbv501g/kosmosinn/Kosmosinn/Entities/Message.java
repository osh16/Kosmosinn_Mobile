package is.hi.hbv501g.kosmosinn.Kosmosinn.Entities;

import javax.persistence.*;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String header;
    @Column(nullable = false)
    private String message;

    // hver sendi skilabod
    @ManyToOne
    private User user;

    // skilabod getur tilheyrt einu spjalli
    @ManyToOne
    private Chat chat;

    public Message() {

    }

    public Message(String header, String message, User user, Chat chat) {
        this.header = header;
        this.message = message;
        this.user = user;
        this.chat = chat;
    }

    public String getHeader() {
        return header;
    }

    public String getMessage() {
        return message;
    }

    public Chat getChat() {
        return chat;
    }

    public User getUser() {
        return user;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
