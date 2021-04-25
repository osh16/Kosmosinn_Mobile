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

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

@RestController
@RequestMapping("/api")
public class LoginRestController {
    private UserService userService;
    private TopicService topicService;
    private BoardService boardService;
    private CommentService commentService;


    @Value("${jwt.token.expiration}")
    private Integer tokenExpirationDate;

    @Value("${jwt.token.secret}")
    private String tokenSecret;

    @Autowired
    public LoginRestController(UserService userService, TopicService topicService, BoardService boardService, CommentService commentService) {
        this.userService = userService;
        this.topicService = topicService;
        this.boardService = boardService;
        this.commentService = commentService;
    }

    @Autowired
    HttpSession session;

    /**
     * Þetta er eiginlega klárað, gæti þurft að breyta einhverju returni fer eftir hvað við viljum senda úr þessu.
     */
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public JSONObject login(@RequestBody() User user, HttpServletResponse response, HttpServletRequest request) throws java.io.IOException {
		User exists = userService.findByUserame(user.getUsername());
        JSONObject res = new JSONObject();
        JSONObject userObj = new JSONObject();

		if (exists != null) {
            if (exists.getPassword().equals(user.getPassword())) {
                String token = getJWTToken(exists.getUsername(), exists.getRole(), exists.getId());
                user.setLastOnline();
                response.setCharacterEncoding("UTF-8");
                response.addHeader("Authorization", token);
                userObj.put("userId", exists.getId());
                userObj.put("username", exists.getUsername());
                userObj.put("userRole", exists.getRole());
                res.put("user", userObj);
                res.put("token", token);
                return res;
            } 
        }

        res.put("error", "uername or password is incorrect");

        return res;
	}

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject signUp(@RequestBody() User user, HttpServletResponse response) {
        User exists = userService.findByUserame(user.getUsername());
        JSONObject res = new JSONObject();
        JSONObject userObj = new JSONObject();
        
        if (exists == null) {
            User signUp = new User(user.getUsername(), user.getPassword(), "USER");
            String token = getJWTToken(signUp.getUsername(), "USER", signUp.getId());
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Authorization", token);
            signUp.setUserCreated();
            signUp.setLastOnline();
            userService.save(signUp);

            User newUser = userService.findByUserame(signUp.getUsername());
            userObj.put("userId", newUser.getId());
            userObj.put("username", newUser.getUsername());
            userObj.put("userRole", newUser.getRole());
            res.put("user", userObj);
            res.put("token", token);
            return res;
        }

        res.put("error", "username is taken");
        return res;
	}

    private String getJWTToken(String username, String role, long id) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
            .commaSeparatedStringToAuthorityList("ROLE_USER");

        if (role.equals("ADMIN")) {
            grantedAuthorities = AuthorityUtils
            .commaSeparatedStringToAuthorityList("ROLE_USER, ROLE_ADMIN");
        }

		String token = Jwts
				.builder()
				.setSubject(username+id)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
                .claim("user", username)
                .claim("userId", id)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + tokenExpirationDate))
                .signWith(SignatureAlgorithm.HS512,
						tokenSecret.getBytes()).compact();

		return "Bearer " + token;
	}

}
