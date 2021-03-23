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

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/comments")
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

    @RequestMapping(value = "/api/comments", method = RequestMethod.GET)
    public List<Comment> getAllComments() {
        return commentService.findAll();
    }

    @PostMapping(value = "{id}/addComment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment addComment(@PathVariable("id") long id, @RequestBody Comment comment) {
        Topic topic = topicService.findById(id).get();
        comment.setTopic(topic);
        commentService.save(comment);
        return comment;
    }

    @PatchMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment editComment(@Valid @PathVariable("id") long id, @RequestBody Comment editedComment) {
        Comment comment = commentService.findById(id).get();
        if (editedComment.getCommentText() != null) {
            comment.setCommentText(editedComment.getCommentText());
            comment.setCommentEdited();
        }
        return comment;
    }

    @DeleteMapping("{id}/delete")
    public void deleteComment(@PathVariable("id") long id) {
        Comment comment = commentService.findById(id).get();
        commentService.delete(comment);
    }
}
