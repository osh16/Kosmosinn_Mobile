package is.hi.hbv501g.kosmosinn.Kosmosinn.Services.Implementations;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Repositories.UserRepository;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
	UserRepository repository;

	@Autowired
	public UserServiceImplementation(UserRepository userRepository) {
		this.repository = userRepository;
	}

	@Override
	public User save(User user) {
		return repository.save(user);
	}

	@Override
	public void delete(User user) {
		repository.delete(user);
	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User findByUserame(String username) {
	    return repository.findByUsername(username);
	}

	@Override
	public Optional<User> findById(long id) {
		return repository.findById(id);
	}

	@Override
	public User login(User user) {
		User exists = findByUserame(user.username);
		if (exists != null) {
			if (exists.getPassword().equals(user.password)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public boolean isAdmin(User user) {
		if (user.getRole().equals("ADMIN"))	{
			return true;
		}
		return false;
	}

	@Override
	public User currentUser(HttpSession session) {
		return (User) session.getAttribute("loggedinuser");
	}

	@Override
	public List<User> findByUsernameContainsIgnoreCase(String query) { return repository.findByUsernameContainsIgnoreCase(query); }
}
