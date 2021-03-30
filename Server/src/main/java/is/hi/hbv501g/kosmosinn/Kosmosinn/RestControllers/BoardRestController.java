package is.hi.hbv501g.kosmosinn.Kosmosinn.RestControllers;

import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Board;
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

import java.util.List;

@RestController
public class BoardRestController {
    private UserService userService;
    private TopicService topicService;
    private BoardService boardService;
    private CommentService commentService;

    @Autowired
    public BoardRestController(UserService userService, TopicService topicService, BoardService boardService, CommentService commentService) {
        this.userService = userService;
        this.topicService = topicService;
        this.boardService = boardService;
        this.commentService = commentService;
    }
    @RequestMapping(value = "/api/boards", method = RequestMethod.GET)
    public List<Board> getAllBoards() {
        return boardService.findAll();
    }

    @RequestMapping(value = "/api/boards/{id}", method = RequestMethod.GET)
    public Board getBoardById(@PathVariable("id") long id) {
        return boardService.findById(id).get();
    }

    @RequestMapping(value = "/api/boards/{id}/topics", method = RequestMethod.GET)
    public List<Topic> getTopicsById(@PathVariable("id") long id) {
        return topicService.findAllByBoardId(id);
    }
}