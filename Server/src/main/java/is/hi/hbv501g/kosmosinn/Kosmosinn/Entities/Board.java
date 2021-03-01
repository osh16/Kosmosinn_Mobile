package is.hi.hbv501g.kosmosinn.Kosmosinn.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Board Entity, an entity for the many boards of Kosmosinn.
 * A Board has an id (long), a name (String), a description (String)
 * and includes a ArrayList of Topics. 
 */
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
