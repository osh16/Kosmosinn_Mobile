package is.hi.hbv601g.kosmosinn_mobile.Entities;

import java.util.List;

public class Board {
    private int id;
    private String name;
    private String description;
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getTopicCount() {
        return topicCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTopicCount(int topicCount) {
        this.topicCount = topicCount;
    }
}
