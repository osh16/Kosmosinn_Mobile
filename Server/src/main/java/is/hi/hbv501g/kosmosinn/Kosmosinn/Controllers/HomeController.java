package is.hi.hbv501g.kosmosinn.Kosmosinn.Controllers;

import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Comment;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.BoardService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.CommentService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.UserService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Topic;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.TopicService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Board;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;

/**
 * This is the controller for the Home page.
 * The page shows you a login and signup buttons,
 * a user list and a button to add new users manually,
 * a list of all boards and, IF you are logged in, a button to add new boards.
 */
@Controller
public class HomeController {

	private UserService userService;
	private TopicService topicService;
	private BoardService boardService;
	private CommentService commentService;

	@Autowired
	public HomeController(UserService userService, TopicService topicService, BoardService boardService, CommentService commentService) {
		this.userService = userService;
		this.topicService = topicService;
		this.boardService = boardService;
		this.commentService = commentService;
	}

	@RequestMapping("/")
	public String Home(Model model) {
	    // fyrsta keyrsla
	    if (userService.findByUserame("oskar") == null) {
	    	User oskar = new User("oskar","oskar","ADMIN", "token");
			userService.save(oskar);

			boardService.save(new Board("frettir um astrad stefansson", "hvad er hann eiginlega ad bralla"));
			boardService.save(new Board("tonlist", "tala um tonlist"));
			boardService.save(new Board("rafmyntir", "hver er næsta 1000x"));
		}
		model.addAttribute("boards", boardService.findAll());
		return "welcome";
	}
}
