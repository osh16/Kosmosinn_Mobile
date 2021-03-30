package is.hi.hbv501g.kosmosinn.Kosmosinn.RestControllers;

import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Board;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Comment;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Topic;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.BoardService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.CommentService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.TopicService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
public class TopicRestController {
    private UserService userService;
    private TopicService topicService;
    private BoardService boardService;
    private CommentService commentService;

    @Autowired
    public TopicRestController(UserService userService, TopicService topicService, BoardService boardService, CommentService commentService) {
        this.userService = userService;
        this.topicService = topicService;
        this.boardService = boardService;
        this.commentService = commentService;
    }
    @Autowired
    HttpSession session;

    @RequestMapping(value = "/api/topics", method = RequestMethod.GET)
    public List<Topic> getAllTopics() {
        return topicService.findAll();
    }

    @RequestMapping(value = "/api/topics/{id}", method = RequestMethod.GET)
    public Topic getTopicById(@PathVariable("id") long id) {
        return topicService.findById(id).get();
    }

    @RequestMapping(value = "/api/topics/{id}/comments", method = RequestMethod.GET)
    public List<Comment> getCommentsById(@PathVariable("id") long id) {
        return commentService.findAllByTopicId(id);
    }

}