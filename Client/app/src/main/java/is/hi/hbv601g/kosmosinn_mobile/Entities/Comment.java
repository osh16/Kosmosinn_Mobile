package is.hi.hbv601g.kosmosinn_mobile.Entities;

public class Comment {
    private int mId;
    private User mUser;
    private String mCommentText;
    private String mCommentCreatedDate;
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
