package is.hi.hbv501g.kosmosinn.Kosmosinn.Repositories;

import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Chat;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Message;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat save(Chat chat);
    void delete(Chat chat);
    List<Chat> findAll();
    List<Message> findAllByUser(User user);
    Optional<Chat> findById(long id);
    Chat findByUserIdAndUserId(long id1, long id2);
    List<Chat> findByUser(User user);
}