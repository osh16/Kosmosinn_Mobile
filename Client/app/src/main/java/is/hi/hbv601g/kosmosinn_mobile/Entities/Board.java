package is.hi.hbv601g.kosmosinn_mobile.Entities;

import java.util.List;

public class Board {
    private static final String ALL_BOARDS = "localhost:8080/boards";
    private int id;
    private String name;
    private String description;
    private List<Topic> topics;
    private int topicCount;


}
