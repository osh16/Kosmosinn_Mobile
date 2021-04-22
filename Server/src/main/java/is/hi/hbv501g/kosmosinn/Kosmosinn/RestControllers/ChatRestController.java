package is.hi.hbv501g.kosmosinn.Kosmosinn.RestControllers;

import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Chat;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Message;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Topic;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.ChatService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.MessageService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/api/chats")
@RestController
public class ChatRestController {

    private MessageService messageService;
    private ChatService chatService;
    private UserService userService;

    public ChatRestController(MessageService messageService, ChatService chatService, UserService userService) {
        this.messageService = messageService;
        this.chatService = chatService;
        this.userService = userService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public List<Message> getMessagesByUser(@PathVariable("id") long id) {
        User user = userService.findById(id).get();
        return messageService.findAllByUser(user);
    }

    @RequestMapping(value = "/cringe", method = RequestMethod.GET)
    public User currentUser(HttpServletRequest request) {
        User currentUser = userService.currentUser(request);
        return currentUser;
    }

    @PostMapping(value = "/{id}/message")
    public void sendMessage(@PathVariable("id") long id, HttpServletRequest request) {
        User currentUser = userService.currentUser(request);
    }
    // get chat which user participate in
    // get all chats
    // send message to user
}
