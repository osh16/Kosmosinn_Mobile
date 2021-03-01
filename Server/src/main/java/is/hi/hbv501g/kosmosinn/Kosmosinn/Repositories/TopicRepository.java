package is.hi.hbv501g.kosmosinn.Kosmosinn.Repositories;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Board;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Topic;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

//public interface TopicRepository extends JpaRepository<Topic, Long> {
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic save(Topic topic);
    void delete(Topic topic);
    List<Topic> findAll();
    Optional<Topic> findById(long id);
    Board findByBoard(long id);
    User findByUser(long id);
    List<Topic> findByOrderByTopicPointsDesc();
    List<Topic> findByOrderByTopicCreatedDesc();
}

