package is.hi.hbv601g.kosmosinn_mobile.Entities;

import java.util.List;

public class User {
    private int id;
    private String username;
    private String role;
    private String userCreatedDate;
    private String lastOnlineDate;
    private List<Comment> comments;
    private List<Topic> topics;

    public User(int id, String username, String role, String userCreatedDate, String lastOnlineDate, List<Comment> comments, List<Topic> topics) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.userCreatedDate = userCreatedDate;
        this.lastOnlineDate = lastOnlineDate;
        this.comments = comments;
        this.topics = topics;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getUserCreatedDate() {
        return userCreatedDate;
    }

    public String getLastOnlineDate() {
        return lastOnlineDate;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUserCreatedDate(String userCreatedDate) {
        this.userCreatedDate = userCreatedDate;
    }

    public void setLastOnlineDate(String lastOnlineDate) {
        this.lastOnlineDate = lastOnlineDate;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}
