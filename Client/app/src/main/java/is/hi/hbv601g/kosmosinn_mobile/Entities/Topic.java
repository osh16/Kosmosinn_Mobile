package is.hi.hbv601g.kosmosinn_mobile.Entities;

import java.util.List;

public class Topic {
    private int mId;
    private String mTopicName;
    private String mTopicContent;
    private int mTopicPoints;
    private int mCommentCount;
    private String mTopicCreatedDate;
    private String mTopicEditedDate;
    private List<Comment> mComments;

    public Topic(int id, String topicName, String topicContent, int topicPoints, int commentCount, String topicCreatedDate, String topicEditedDate) {
        mId = id;
        mTopicName = topicName;
        mTopicContent = topicContent;
        mTopicPoints = topicPoints;
        mCommentCount = commentCount;
        mTopicCreatedDate = topicCreatedDate;
        mTopicEditedDate = topicEditedDate;
    }

    public int getId() {
        return mId;
    }

    public String getTopicName() {
        return mTopicName;
    }

    public String getTopicContent() {
        return mTopicContent;
    }

    public int getTopicPoints() {
        return mTopicPoints;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public String getTopicCreatedDate() {
        return mTopicCreatedDate;
    }

    public String getTopicEditedDate() {
        return mTopicEditedDate;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setTopicName(String topicName) {
        mTopicName = topicName;
    }

    public void setTopicContent(String topicContent) {
        mTopicContent = topicContent;
    }

    public void setTopicPoints(int topicPoints) {
        mTopicPoints = topicPoints;
    }

    public void setCommentCount(int commentCount) {
        mCommentCount = commentCount;
    }

    public void setTopicCreatedDate(String topicCreatedDate) {
        mTopicCreatedDate = topicCreatedDate;
    }

    public void setTopicEditedDate(String topicEditedDate) {
        mTopicEditedDate = topicEditedDate;
    }
}
