package is.hi.hbv501g.kosmosinn.Kosmosinn.Services.Implementations;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Repositories.UserRepository;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Value;

@Service
public class UserServiceImplementation implements UserService {
	UserRepository repository;

	@Value("${jwt.token.secret}")
    private String tokenSecret;

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
	public User currentUser(HttpServletRequest request) {
		String token = request.getHeader("Authorization").replace("Bearer ", "");
		Claims claims = Jwts.parser().setSigningKey(tokenSecret.getBytes()).parseClaimsJws(token).getBody();
		int id = Integer.parseInt(claims.get("userId").toString());
		return repository.findById(id).get();
	}

	@Override
	public User currentUser(HttpServletRequest request) {
		String token = request.getHeader("Authorization").replace("Bearer ", "");
		Claims claims = Jwts.parser().setSigningKey(tokenSecret.getBytes()).parseClaimsJws(token).getBody();
		int id = Integer.parseInt(claims.get("userId").toString());
		return repository.findById(id).get();
	}
}
