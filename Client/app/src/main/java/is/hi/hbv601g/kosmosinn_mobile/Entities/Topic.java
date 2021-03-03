package is.hi.hbv601g.kosmosinn_mobile.Entities;

import java.util.List;

public class Topic {
    private int id;
    private String topicName;
    private String topicContent;
    private int topicPoints;
    private int commentCount;
    private String topicCreatedDate;
    private String topicEditedDate;
    private List<Comment> comments;

    public Topic(int id, String topicName, String topicContent, int topicPoints, int commentCount, String topicCreatedDate, String topicEditedDate) {
        this.id = id;
        this.topicName = topicName;
        this.topicContent = topicContent;
        this.topicPoints = topicPoints;
        this.commentCount = commentCount;
        this.topicCreatedDate = topicCreatedDate;
        this.topicEditedDate = topicEditedDate;
    }

    public int getId() {
        return id;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public int getTopicPoints() {
        return topicPoints;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public String getTopicCreatedDate() {
        return topicCreatedDate;
    }

    public String getTopicEditedDate() {
        return topicEditedDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    public void setTopicPoints(int topicPoints) {
        this.topicPoints = topicPoints;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setTopicCreatedDate(String topicCreatedDate) {
        this.topicCreatedDate = topicCreatedDate;
    }

    public void setTopicEditedDate(String topicEditedDate) {
        this.topicEditedDate = topicEditedDate;
    }
}
