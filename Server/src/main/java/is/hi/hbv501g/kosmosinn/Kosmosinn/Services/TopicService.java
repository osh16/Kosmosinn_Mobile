package is.hi.hbv501g.kosmosinn.Kosmosinn.Services;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Board;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Topic;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;

import java.util.List;
import java.util.Optional;

public interface TopicService {
    Topic save(Topic topic);
    void delete(Topic topic);
    List<Topic> findAll();
    Optional<Topic> findById(long id);
    Board findByBoard(long id);
    List<Topic> findByPopularTopicsByBoard(long id);
    List<Topic> findByNewTopicsByBoard(long id);
    User findByUser(long id);// thread creator
    List<Topic> findAllByBoardId(long id);
    List<Topic> findAllByUserId(long id);
    List<Topic> findByOrderByTopicPointsDesc();
    List<Topic> findByOrderByTopicCreatedDesc();
}