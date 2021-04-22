package is.hi.hbv501g.kosmosinn.Kosmosinn.RestControllers;

import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Board;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/topics/{topicId}")
@RestController
public class CommentRestController {
    private UserService userService;
    private TopicService topicService;
    private BoardService boardService;
    private CommentService commentService;

    @Autowired
    public CommentRestController(UserService userService, TopicService topicService, BoardService boardService, CommentService commentService) {
        this.userService = userService;
        this.topicService = topicService;
        this.boardService = boardService;
        this.commentService = commentService;
    }

    @Autowired
    HttpSession session;

    /* Þetta ætti að virka núna
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Comment> getAllComments() {
        return commentService.findAll();
    }
     */

    @PostMapping(value = "/addComment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment addComment(@PathVariable("topicId") long id, @RequestBody Comment comment, HttpServletRequest request) {
        Topic topic = topicService.findById(id).get();

        comment.setUser(userService.findByUserame(comment.getUser().getUsername()));
        comment.setTopic(topic);
        comment.setCommentCreated();
        comment.setCommentEdited();
        commentService.save(comment);
        
        return comment;
    }

    @PatchMapping(value = "/editComment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment editComment(@Valid @PathVariable("topicId") long topicId, @Valid @PathVariable("id") long id, @RequestBody Comment editedComment, HttpServletRequest request) {
        Topic topic = topicService.findById(topicId).get();
        Comment comment = commentService.findById(id).get();
        if (isAllowed(comment, request)) {
            if (editedComment.getCommentText() != null) {
                comment.setCommentText(editedComment.getCommentText());
                comment.setCommentEdited();
            }
        }
        return comment;
    }

    @DeleteMapping("/deleteComment/{id}")
    public void deleteComment(@Valid @PathVariable("topicId") long topicId, @PathVariable("id") long id, HttpServletRequest request) {
        //User currentUser = (User) session.getAttribute("loggedinuser");
        Topic topic = topicService.findById(topicId).get();
        Comment comment = commentService.findById(id).get();
        if (isAllowed(comment, request)) {
            commentService.delete(comment);
        }
    }

    private Boolean isAllowed(Comment comment, HttpServletRequest request) {
        User currentUser = userService.currentUser(request);

        if (userService.isAdmin(currentUser) || commentService.findAllByUserId(currentUser.getId()).contains(comment)) {
            return true;
        }

        return false;
    }

    private Boolean isAllowed(HttpServletRequest request) {
        User currentUser = userService.currentUser(request);

        if (!currentUser.getRole().isEmpty()) {
            return true;
        }

        return false;
    }
}
