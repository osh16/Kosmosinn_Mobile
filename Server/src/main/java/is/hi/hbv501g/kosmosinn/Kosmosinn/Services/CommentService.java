package is.hi.hbv501g.kosmosinn.Kosmosinn.Services;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Comment;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Topic;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment save(Comment comment);
    void delete(Comment comment);
    List<Comment> findAll();
    Optional<Comment> findById(long id);
    Topic findByTopic(long id);
    User findByUser(long id);// thread creator
    List<Comment> findAllByTopicId(long id);
    List<Comment> findAllByUserId(long id);
}