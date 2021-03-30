package is.hi.hbv601g.kosmosinn_mobile.Entities;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("id")
    private int mId;
    @SerializedName("user")
    private User mUser;
    @SerializedName("commentText")
    private String mCommentText;
    @SerializedName("commentCreated")
    private String mCommentCreatedDate;
    @SerializedName("commentEdited")
    private String mCommentEditedDate;

    public Comment(int id, User user, String commentText, String commentCreatedDate, String commentEditedDate) {
        mId = id;
        mUser = user;
        mCommentText = commentText;
        mCommentCreatedDate = commentCreatedDate;
        mCommentEditedDate = commentEditedDate;
    }

    public int getId() {
        return mId;
    }

    public User getUser() {
        return mUser;
    }

    public String getCommentText() {
        return mCommentText;
    }

    public String getCommentCreatedDate() {
        return mCommentCreatedDate;
    }

    public String getCommentEditedDate() {
        return mCommentEditedDate;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public void setCommentText(String commentText) {
        mCommentText = commentText;
    }

    public void setCommentCreatedDate(String commentCreatedDate) {
        mCommentCreatedDate = commentCreatedDate;
    }

    public void setCommentEditedDate(String commentEditedDate) {
        mCommentEditedDate = commentEditedDate;
    }
}
