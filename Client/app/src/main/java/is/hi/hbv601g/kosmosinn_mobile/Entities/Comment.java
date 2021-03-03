package is.hi.hbv601g.kosmosinn_mobile.Entities;

public class Comment {
    private int id;
    private User user;
    private String commentText;
    private String commentCreatedDate;
    private String commentEditedDate;

    public Comment(int id, User user, String commentText, String commentCreatedDate, String commentEditedDate) {
        this.id = id;
        this.user = user;
        this.commentText = commentText;
        this.commentCreatedDate = commentCreatedDate;
        this.commentEditedDate = commentEditedDate;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getCommentCreatedDate() {
        return commentCreatedDate;
    }

    public String getCommentEditedDate() {
        return commentEditedDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setCommentCreatedDate(String commentCreatedDate) {
        this.commentCreatedDate = commentCreatedDate;
    }

    public void setCommentEditedDate(String commentEditedDate) {
        this.commentEditedDate = commentEditedDate;
    }
}
