package is.hi.hbv501g.kosmosinn.Kosmosinn.Entities;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String header;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private User from;

    @ManyToOne
    private User to;

    @Column
    private long messageCreated;

    public Message() {
    }

    public long getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public String getContent() {
        return content;
    }

    public User getFrom() {
        return from;
    }

    public User getTo() {
        return to;
    }

    public String getMessageCreated() {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochSecond(messageCreated), ZoneId.systemDefault());
        return String.format("%d.%d.%d, %d:%d:%d", ldt.getDayOfMonth(), ldt.getDayOfMonth(), ldt.getYear(), ldt.getHour(), ldt.getMinute(), ldt.getSecond());
    }


    public void setHeader(String header) {
        this.header = header;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public void setMessageCreated() {
        this.messageCreated = Instant.now().getEpochSecond();
    }

}
