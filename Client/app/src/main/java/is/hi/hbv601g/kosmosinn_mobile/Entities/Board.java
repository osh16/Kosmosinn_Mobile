package is.hi.hbv601g.kosmosinn_mobile.Entities;

import java.util.List;

public class Board {
    private int id;
    private String name;
    private String description;
    private List<Topic> topics;
    private int topicCount;

    public Board(int id, String name, String description, List<Topic> topics, int topicCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.topics = topics;
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

    public List<Topic> getTopics() {
        return topics;
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

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public void setTopicCount(int topicCount) {
        this.topicCount = topicCount;
    }
}
