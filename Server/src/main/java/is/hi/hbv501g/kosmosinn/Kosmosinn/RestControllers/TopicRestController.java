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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;

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
    public Topic getTopicById(@PathVariable("id") long id, HttpServletRequest request) {
        
        return topicService.findById(id).get();
    }

    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    public List<Comment> getCommentsById(@PathVariable("id") long id) {
        return commentService.findAllByTopicId(id);
    }


    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Topic editTopic(@Valid @PathVariable("id") long id, @RequestBody Topic editedTopic, HttpServletRequest request) {
        Topic topic = topicService.findById(id).get();
        
        
        if (isAllowed(topic, request)) {
            if (editedTopic.getTopicName() != null) {
                topic.setTopicName(editedTopic.getTopicName());
            }
            if (editedTopic.getTopicContent() != null) {
                topic.setTopicContent(editedTopic.getTopicContent());
            }
            topicService.save(topic);
        //}
            return topic;
        }

        return null;

    }

    @DeleteMapping("/{id}/delete")
    public void deleteTopic(@PathVariable("id") long id, HttpServletRequest request) {
        Topic topic = topicService.findById(id).get();
        if (isAllowed(topic, request)) {
            topicService.delete(topic);
        }
    }

    private Boolean isAllowed(Topic topic, HttpServletRequest request) {
        User currentUser = userService.currentUser(request);

        if (userService.isAdmin(currentUser) || topicService.findAllByUserId(currentUser.getId()).contains(topic)) {
            return true;
        }

        return false;
    }
}
