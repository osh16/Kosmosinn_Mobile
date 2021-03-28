package is.hi.hbv501g.kosmosinn.Kosmosinn.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Topic Entity, an entity for the many topics of Kosmosinn.
 * A Topic has an id (long), an ArrayList of Comments (Comment Entities),
 * a designated Board (Board Entitiy), a designated User (User Entity), 
 * a topicName (String), a topicContent (String) and topicPoints (int) currently not implemented fully.
 */
@Entity
public class Topic{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@JsonView
    @JsonIgnore // nota /api/topics/{id}/comments
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "topic", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @JsonBackReference
    @ManyToOne
    private Board board;

    @JsonBackReference
    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String topicName;

    private int topicPoints;

    @Column(length=100000)
    private String topicContent;

    private long topicCreated;

    private long topicEdited;

    public Topic() {
    }

    public Topic(String topicName, int topicPoints, String topicContent) {
        this.topicName = topicName;
        this.topicPoints = topicPoints;
        this.topicContent = topicContent;
    }

    public long getId() {
        return id;
    }
    public String getTopicName() {
        return topicName;
    }
    public int getTopicPoints() {
        return topicPoints;
    }
    public String getTopicContent() {
        return topicContent;
    }

    public List<Comment> getComments() {
        return comments;
    }
    public int getCommentCount() {
        return comments.size();
    }
    public Board getBoard() {
        return board;
    }
    public User getUser() {
        return user;
    }

    public String getTopicCreatedDate() {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochSecond(topicCreated), ZoneId.systemDefault());
        return String.format("%d.%d.%d, %d:%d:%d", ldt.getDayOfMonth(), ldt.getDayOfMonth(), ldt.getYear(), ldt.getHour(), ldt.getMinute(), ldt.getSecond());
    }

    public String getTopicEditedDate() {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochSecond(topicEdited), ZoneId.systemDefault());
        return String.format("%d.%d.%d, %d:%d:%d", ldt.getDayOfMonth(), ldt.getDayOfMonth(), ldt.getYear(), ldt.getHour(), ldt.getMinute(), ldt.getSecond());
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    public void setTopicPoints(int topicPoints) {
        this.topicPoints = topicPoints;
    }
    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    public void setBoard(Board board) {
        this.board = board;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public void setTopicCreated() {
        this.topicCreated = Instant.now().getEpochSecond();
    }

    public void setTopicEdited() {
        this.topicEdited = Instant.now().getEpochSecond();
    }
}