package is.hi.hbv601g.kosmosinn_mobile.Entities;

import androidx.room.Entity;

@Entity
public class Board {






    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(length=100000)
    private String description;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Topic> topics = new ArrayList<>();

    public Board() {
    }

    public Board(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getId() {
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
        return topics.size();
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
}
