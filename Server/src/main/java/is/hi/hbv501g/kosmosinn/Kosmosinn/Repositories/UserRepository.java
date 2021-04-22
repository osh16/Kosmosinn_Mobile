package is.hi.hbv501g.kosmosinn.Kosmosinn.Repositories;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Topic;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	User save(User user);
	void delete(User user);
	List<User> findAll();
	User findByUsername(String username);
	Optional<User> findById(long id);
	List<User> findByUsernameContainsIgnoreCase(String query);
}
