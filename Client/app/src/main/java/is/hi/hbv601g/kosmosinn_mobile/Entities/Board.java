package is.hi.hbv601g.kosmosinn_mobile.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Board {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("topics")
    private List<Topic> mTopics;

    public Board(int id, String name, String description, int topicCount) {
        mId = id;
        mName = name;
        mDescription = description;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getTopicCount() {
        return mTopics.size();
    }
}
