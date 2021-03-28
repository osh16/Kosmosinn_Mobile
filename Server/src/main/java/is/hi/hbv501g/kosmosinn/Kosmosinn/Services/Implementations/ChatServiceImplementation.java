package is.hi.hbv501g.kosmosinn.Kosmosinn.Services.Implementations;

import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Chat;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Repositories.ChatRepository;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.ChatService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImplementation implements ChatService {
    ChatRepository repository;
    @Override
    public Chat save(Chat chat) {
        return repository.save(chat);
    }

    @Override
    public void delete(Chat chat) {
        repository.delete(chat);
    }

    @Override
    public List<Chat> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Chat> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Chat> findByUser(User user) {
        return null;
    }
}
