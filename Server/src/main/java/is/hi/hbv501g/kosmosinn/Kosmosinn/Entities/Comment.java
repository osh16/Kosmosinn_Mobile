package is.hi.hbv501g.kosmosinn.Kosmosinn.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Comment Entity, an entity for the many comments of Kosmosinn.
 * A Comment has an id (long), a designated user (User Entity), a designated Topic (Topic Entity)
 * and comment text (String).
 */
@Entity
public class Comment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @JsonBackReference
    @ManyToOne
    private User user;

    @JsonBackReference
    @ManyToOne
    private Topic topic;

    private long commentCreated;

    private long commentEdited;

    @Column(length=10000)
    private String commentText;

    //@Column(nullable = true, length = 64)
    //private String photo;

    public Comment(User user, String commentText) {
        this.user = user;
        this.commentText = commentText;
    }

    public Comment(User user) {
        this.user = user;
    }
    public Comment(String commentText) {
        this.commentText = commentText;
    }

    public long getId() {
        return id;
    }

    public String getCommentText() {
        return commentText;
    }

    public User getUser() {
        return user;
    }

    public Topic getTopic() {
        return topic;
    }

    public String getCommentCreatedDate() {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochSecond(commentCreated), ZoneId.systemDefault());
        return String.format("%d.%d.%d, %d:%d:%d", ldt.getDayOfMonth(), ldt.getDayOfMonth(), ldt.getYear(), ldt.getHour(), ldt.getMinute(), ldt.getSecond());
    }

    public String getCommentEditedDate() {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochSecond(commentEdited), ZoneId.systemDefault());
        return String.format("%d.%d.%d, %d:%d:%d", ldt.getDayOfMonth(), ldt.getDayOfMonth(), ldt.getYear(), ldt.getHour(), ldt.getMinute(), ldt.getSecond());
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setCommentCreated() {
        this.commentCreated = Instant.now().getEpochSecond();
    }

    public void setCommentEdited() {
        this.commentEdited = Instant.now().getEpochSecond();
    }
}