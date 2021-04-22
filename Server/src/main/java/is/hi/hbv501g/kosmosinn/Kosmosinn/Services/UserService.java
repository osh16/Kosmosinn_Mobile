package is.hi.hbv501g.kosmosinn.Kosmosinn.Services;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface UserService {
	User save(User user);
	void delete(User user);
	List<User> findAll();
	User findByUserame(String username);
	Optional<User> findById(long id);
	User login(User user);
	User currentUser(HttpServletRequest request);
	boolean isAdmin(User user);
}