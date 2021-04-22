package is.hi.hbv501g.kosmosinn.Kosmosinn.Services.Implementations;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Board;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Comment;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Topic;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Repositories.CommentRepository;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImplementation implements CommentService {
    CommentRepository repository;

    @Autowired
    public CommentServiceImplementation(CommentRepository commentRepository) {
        this.repository = commentRepository;
    }

    @Override
    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        repository.delete(comment);
    }

    @Override
    public List<Comment> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Comment> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public Topic findByTopic(long id) {
        return repository.findByTopic(id);
    }

    @Override
    public User findByUser(long id) {
        return repository.findByUser(id);
    }

    @Override
    public List<Comment> findAllByTopicId(long id) {
        List<Comment> comments = repository.findAll();
        List<Comment> commentsByTopicId = new ArrayList<Comment>();
        for (Comment c : comments) {
            if (c.getTopic().getId() == id)  {
                commentsByTopicId.add(c);
            }
        }
        return commentsByTopicId;
    }

    @Override
    public List<Comment> findAllByUserId(long id) {
        List<Comment> comments = repository.findAll();
        List<Comment> commentsByUserId = new ArrayList<Comment>();
        for(Comment c : comments) {
            if (c.getUser().getId() == id) {
                commentsByUserId.add(c);
            }
        }
        return commentsByUserId;
    }

    @Override
    public List<Comment> findAllByUserprofileId(long id) {
        List<Comment> comments = repository.findAll();
        List<Comment> commentsByUserprofileId = new ArrayList<Comment>();
        for(Comment c : comments) {
            if (c.getUserprofile() != null) {
                if (c.getUserprofile().getId() == id) {
                    commentsByUserprofileId.add(c);
                }
            }
        }
        return commentsByUserprofileId;
    }
}