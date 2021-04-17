package is.hi.hbv601g.kosmosinn_mobile.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Topic {
    @SerializedName("id")
    private int mId;
    @SerializedName("user")
    private User mUser;
    @SerializedName("topicName")
    private String mTopicName;
    @SerializedName("topicContent")
    private String mTopicContent;
    @SerializedName("topicPoints")
    private int mTopicPoints;
    @SerializedName("commentCount")
    private int mCommentCount;
    @SerializedName("topicCreatedDate")
    private String mTopicCreatedDate;
    @SerializedName("topicEditedDate")
    private String mTopicEditedDate;
    @SerializedName("comments")
    private List<Comment> mComments;

    public Topic(int id, User user, String topicName, String topicContent, int topicPoints, int commentCount, String topicCreatedDate, String topicEditedDate) {
        mId = id;
        mUser = user;
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

    public User getUser() {
        return mUser;
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

    public void setUser(User mUser) {
        this.mUser = mUser;
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
