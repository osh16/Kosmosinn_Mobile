package is.hi.hbv501g.kosmosinn.Kosmosinn.RestControllers;

import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Comment;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Topic;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.BoardService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.CommentService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.TopicService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private UserService userService;
    private TopicService topicService;
    private BoardService boardService;
    private CommentService commentService;

    @Autowired
    public UserRestController(UserService userService, TopicService topicService, BoardService boardService, CommentService commentService) {
        this.userService = userService;
        this.topicService = topicService;
        this.boardService = boardService;
        this.commentService = commentService;
    }

    @Autowired
    HttpSession session;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") long id) {
        return userService.findById(id).get();
    }

    @RequestMapping(value = "/{id}/topics", method = RequestMethod.GET)
    public List<Topic> getTopicsByUser(@PathVariable("id") long id) {
        return topicService.findAllByUserId(id);
    }

    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    public List<Comment> getCommentsByUser(@PathVariable("id") long id) {
        return commentService.findAllByUserId(id);
    }

    @PostMapping(value = "/addUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user) {
        user.setUserCreated();
        user.setLastOnline();
        user.setRole("USER");
        userService.save(user);
        return user;
    }

    @RequestMapping(value = "/search/{query}", method = RequestMethod.GET)
    public List<User> getUsersBySearch(@PathVariable("query") String query) { return userService.findByUsernameContainsIgnoreCase(query); }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User editUser(@Valid @PathVariable("id") long id, @RequestBody User editedUser) {
        //User currentUser = (User) session.getAttribute("loggedinuser");
        User user = userService.findById(id).get();
        //if (userService.isAdmin(currentUser) || user.getId() == currentUser.getId()) {
            if (editedUser.getUsername() != null) {
                user.setUsername(editedUser.getUsername());
            }
            if (editedUser.getPassword() != null) {
                user.setPassword(editedUser.getPassword());
            }
            userService.save(user);
        //}
        return user;
    }

    @DeleteMapping("/{id}/delete")
    public void deleteUser(@PathVariable("id") long id) {
        User currentUser = (User) session.getAttribute("loggedinuser");
        User user = userService.findById(id).get();
        if (userService.isAdmin(currentUser) || user.getId() == currentUser.getId()) {
            userService.delete(user);
        }
    }

    private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
