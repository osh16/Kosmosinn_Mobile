package is.hi.hbv501g.kosmosinn.Kosmosinn.Services.Implementations;

import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Message;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Repositories.MessageRepository;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MessageServiceImplementation implements MessageService {
    MessageRepository repository;

    @Autowired
    public MessageServiceImplementation(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Message save(Message message) {
        return repository.save(message);
    }

    @Override
    public void delete(Message message) {
        repository.delete(message);
    }

    @Override
    public List<Message> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Message> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Message> findByFrom(User from) {
        return repository.findByFrom(from);
    }

    @Override
    public List<Message> findByTo(User to) {
        return repository.findByTo(to);
    }
}
