package is.hi.hbv501g.kosmosinn.Kosmosinn.RestControllers;

import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Chat;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Message;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Topic;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.ChatService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.MessageService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/messages")
@RestController
public class ChatRestController {

    private MessageService messageService;
    private ChatService chatService;
    private UserService userService;

    @Autowired
    public ChatRestController(MessageService messageService, ChatService chatService, UserService userService) {
        this.messageService = messageService;
        this.chatService = chatService;
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Message> getAllMessages() {
        return messageService.findAll();
    }

    // Fa oll messages a milli tveggja notenda
    @RequestMapping(value = "/{from}/{to}", method = RequestMethod.GET)
    public List<Message> getChatBetweenUsers(@PathVariable("from") long from, @PathVariable("to") long to, HttpServletRequest request) {
        User userFrom = userService.findById(from).get();
        User userTo = userService.findById(to).get();
        List<Message> messagesFrom = userFrom.getMessagesSent();
        List<Message> messagesTo = userTo.getMessagesReceived();
        return messagesFrom.stream().filter(messagesTo::contains).collect(Collectors.toList());
    }

    @RequestMapping(value = "/getMessagesToYou", method = RequestMethod.GET)
    public List<Message> getAllMessagesSentToYou(HttpServletRequest request) {
        User currentUser = userService.currentUser(request);
        //List<Message> messagesTo = messageService.findByTo(currentUser);
        return currentUser.getMessagesReceived();
    }

    @RequestMapping(value = "/getMessagesFromYou", method = RequestMethod.GET)
    public List<Message> getAllMessagesSentFromYou(HttpServletRequest request) {
        User currentUser = userService.currentUser(request);
        //List<Message> messagesTo = messageService.findByFrom(currentUser);
        return currentUser.getMessagesSent();
    }

    @RequestMapping(value = "/getMessages", method = RequestMethod.GET)
    public List<Message> getAllMessages(HttpServletRequest request) {
        User currentUser = userService.currentUser(request);
        //List<Message> messagesFrom = messageService.findByFrom(currentUser);
        //List<Message> messagesTo = messageService.findByTo(currentUser);
        List<Message> messagesFrom = currentUser.getMessagesSent();
        List<Message> messagesTo = currentUser.getMessagesReceived();
        return messagesFrom.stream().filter(messagesTo::contains).collect(Collectors.toList());
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendMessage(@PathVariable("id") long id, @RequestBody Message message, HttpServletRequest request) {
        if (request.getHeader("Authorization") != null) {
            User fromUser = userService.currentUser(request);
            User toUser = userService.findById(id).get();

            message.setFrom(fromUser);
            message.setTo(toUser);
            message.setMessageCreated();
            messageService.save(message);

            fromUser.getMessagesSent().add(message);
            userService.save(fromUser);

            toUser.getMessagesReceived().add(message);
            userService.save(toUser);
        }
    }
}
