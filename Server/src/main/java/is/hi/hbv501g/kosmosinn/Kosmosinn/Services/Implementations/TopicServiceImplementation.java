package is.hi.hbv501g.kosmosinn.Kosmosinn.Services.Implementations;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Board;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Topic;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Repositories.TopicRepository;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.TopicService;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImplementation implements TopicService {
    TopicRepository repository;

    @Autowired
    public TopicServiceImplementation(TopicRepository topicRepository) {
        this.repository = topicRepository;
    }

    @Override
    public Topic save(Topic topic) {
        return repository.save(topic);
    }

    @Override
    public void delete(Topic topic) {
        repository.delete(topic);
    }

    @Override
    public List<Topic> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Topic> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public Board findByBoard(long id) {
        return repository.findByBoard(id);
    }

    @Override
    public User findByUser(long id) {
        return repository.findByUser(id);
    }

    @Override
    public List<Topic> findByPopularTopicsByBoard(long id) {
        List<Topic> topics = repository.findByOrderByTopicPointsDesc();
        List<Topic> popularTopics = new ArrayList<>();
        for (Topic t : topics) {
            if (t.getBoard().getId() == id) {
                popularTopics.add(t);
            }
        }
        return popularTopics;
    }

    @Override
    public List<Topic> findByNewTopicsByBoard(long id) {
        List<Topic> topics = repository.findByOrderByTopicCreatedDesc();
        List<Topic> newTopics = new ArrayList<>();
        for (Topic t : topics) {
            if (t.getBoard().getId() == id) {
                newTopics.add(t);
            }
        }
        return newTopics;
    }
    @Override
    public List<Topic> findAllByBoardId(long id) {
        List<Topic> topics = repository.findAll();
        List<Topic> topicsByBoardId = new ArrayList<Topic>();
        for(Topic t : topics) {
            if (t.getBoard().getId() == id) {
               topicsByBoardId.add(t);
            }
        }
        return topicsByBoardId;
    }

    @Override
    public List<Topic> findAllByUserId(long id) {
        List<Topic> topics = repository.findAll();
        List<Topic> topicsByUserId = new ArrayList<Topic>();
        for(Topic t : topics) {
            if (t.getUser().getId() == id) {
                topicsByUserId.add(t);
            }
        }
        return topicsByUserId;
    }

    @Override
    public List<Topic> findByOrderByTopicPointsDesc() { return repository.findByOrderByTopicPointsDesc(); }

    @Override
    public List<Topic> findByOrderByTopicCreatedDesc() { return repository.findByOrderByTopicCreatedDesc(); }
}