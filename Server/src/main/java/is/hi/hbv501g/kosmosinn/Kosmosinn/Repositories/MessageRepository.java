package is.hi.hbv501g.kosmosinn.Kosmosinn.Repositories;

import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Message;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Message save(Message message);
    void delete(Message message);
    List<Message> findAll();
    List<Message> findByFrom(User from);
    List<Message> findByTo(User to);
    Optional<Message> findById(long id);
}
