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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/topics")
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

    @RequestMapping(method = RequestMethod.GET)
    public List<Topic> getAllTopics() {
        return topicService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Topic getTopicById(@PathVariable("id") long id) {
        return topicService.findById(id).get();
    }

    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    public List<Comment> getCommentsById(@PathVariable("id") long id) {
        return commentService.findAllByTopicId(id);
    }


    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Topic editTopic(@Valid @PathVariable("id") long id, @RequestBody Topic editedTopic) {
        Topic topic = topicService.findById(id).get();
        User currentUser = (User) session.getAttribute("loggedinuser");
        User user = userService.findById(id).get();
        if (userService.isAdmin(currentUser) || user.getId() == currentUser.getId()) {
            if (editedTopic.getTopicName() != null) {
                topic.setTopicName(editedTopic.getTopicName());
            }
            if (editedTopic.getTopicContent() != null) {
                topic.setTopicContent(editedTopic.getTopicContent());
            }
            topicService.save(topic);
        }
        return topic;
    }

    @DeleteMapping("/{id}/delete")
    public void deleteTopic(@PathVariable("id") long id) {
        //User currentUser = (User) session.getAttribute("loggedinuser");
        //if (userService.isAdmin(currentUser)) {
            Topic topic = topicService.findById(id).get();
            topicService.delete(topic);
        //}
    }

    @PostMapping(value = "/{id}/addComment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment addComment(@PathVariable("topicId") long topicId, @PathVariable("id") long id, @RequestBody Comment comment) {
        //User currentUser = (User) session.getAttribute("loggedinuser");
        //if (comment.getUser().getId() == currentUser.getId()) {
        Topic topic = topicService.findById(id).get();
        comment.setTopic(topic);
        commentService.save(comment);
        //}
        return comment;
    }
}
