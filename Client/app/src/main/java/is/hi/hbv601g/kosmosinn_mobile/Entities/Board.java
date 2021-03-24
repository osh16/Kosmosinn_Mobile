package is.hi.hbv601g.kosmosinn_mobile.Entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Board {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("topicCount")
    private int topicCount;

    public Board(int id, String name, String description, int topicCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.topicCount = topicCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(int topicCount) {
        this.topicCount = topicCount;
    }
}
