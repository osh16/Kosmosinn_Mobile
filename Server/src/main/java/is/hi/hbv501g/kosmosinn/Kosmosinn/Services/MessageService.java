package is.hi.hbv501g.kosmosinn.Kosmosinn.Services;

import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Message;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Message save(Message message);
    void delete(Message message);
    List<Message> findAll();
    Optional<Message> findById(long id);
    List<Message> findByUser();
    List<Message> findAllByUser(User user);
}
