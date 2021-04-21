package is.hi.hbv501g.kosmosinn.Kosmosinn.RestControllers;

import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Chat;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Message;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Topic;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.ChatService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.MessageService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RequestMapping("/api/chats")
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

    @PostMapping(value = "/{id}/message")
    public void sendMessage(@PathVariable("id") long id) {
        User currentUser;
        Chat chat = chatService.findByUserIdAndUserId()
    }
    // get chat which user participate in
    // get all chats
    // send message to user
}
