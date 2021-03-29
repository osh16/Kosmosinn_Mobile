package is.hi.hbv601g.kosmosinn_mobile.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName("id")
    private int mId;
    @SerializedName("username")
    private String mUsername;
    @SerializedName("role")
    private String mRole;
    @SerializedName("userCreatedDate")
    private String mUserCreatedDate;
    @SerializedName("lastOnlineDate")
    private String mLastOnlineDate;
    @SerializedName("comments")
    private List<Comment> mComments;
    @SerializedName("topics")
    private List<Topic> mTopics;


    public User(int id, String username, String role, String userCreatedDate, String lastOnlineDate, List<Comment> comments, List<Topic> topics) {
        mId = id;
        mUsername = username;
        mRole = role;
        mUserCreatedDate = userCreatedDate;
        mLastOnlineDate = lastOnlineDate;
        mComments = comments;
        mTopics = topics;
    }

    public int getId() {
        return mId;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getRole() {
        return mRole;
    }

    public String getUserCreatedDate() {
        return mUserCreatedDate;
    }

    public String getLastOnlineDate() {
        return mLastOnlineDate;
    }

    public List<Comment> getComments() {
        return mComments;
    }

    public List<Topic> getTopics() {
        return mTopics;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setRole(String role) {
        mRole = role;
    }

    public void setUserCreatedDate(String userCreatedDate) {
        mUserCreatedDate = userCreatedDate;
    }

    public void setLastOnlineDate(String lastOnlineDate) {
        mLastOnlineDate = lastOnlineDate;
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
    }

    public void setTopics(List<Topic> topics) {
        mTopics = topics;
    }
}
