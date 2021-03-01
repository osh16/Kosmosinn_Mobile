package is.hi.hbv501g.kosmosinn.Kosmosinn.Controllers;

import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.Board;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Entities.User;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.BoardService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.CommentService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.TopicService;
import is.hi.hbv501g.kosmosinn.Kosmosinn.Services.UserService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private TopicService topicService;
    private UserService userService;
    private BoardService boardService;
    private CommentService commentService;

    @Autowired
    public UserController(UserService userService, TopicService topicService, BoardService boardService, CommentService commentService) {
        this.boardService = boardService;
        this.userService = userService;
        this.topicService = topicService;
        this.commentService = commentService;
    }

    @RequestMapping(value="/user/{id}", method = RequestMethod.GET)
    public String userProfile(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID")));

        if (topicService.findAllByUserId(id) != null) {
            model.addAttribute("topics", topicService.findAllByUserId(id));
        }

        if (commentService.findAllByUserId(id) != null) {
            model.addAttribute("comments", commentService.findAllByUserId(id));
        }
        return "user-profile";
    }

    @RequestMapping(value="/userlist", method = RequestMethod.GET)
    public String getUserList(@Valid User user, BindingResult result, Model model, HttpSession session) {
        User currentUser = userService.findByUserame(((User) session.getAttribute("loggedinuser")).getUsername());
        boolean isAdmin = userService.isAdmin(currentUser);
        if (!isAdmin) {
            return "redirect:/";
        }
        model.addAttribute("users", userService.findAll());
        return "user-list";

    }
    /**
     * Function addUser, this function creates a new user and redirects you back to
     * the Welcome page.
     */
    @RequestMapping(value="/adduser", method = RequestMethod.POST)
    public String addUser(@Valid User user, BindingResult result, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedinuser");
        boolean isAdmin = userService.isAdmin(currentUser);
        if (!isAdmin) {
            return "redirect:/";
        }
        user.setUserCreated();
        user.setRole("USER");
        userService.save(user);
        return "redirect:/userlist";
    }

    /**
     * Function addUserForm returns and redirects to the form to add a user.
     */
    @RequestMapping(value="/adduser", method = RequestMethod.GET)
    public String addUserForm(User user, HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedinuser");
        boolean isAdmin = userService.isAdmin(currentUser);
        if (!isAdmin) {
            return "redirect:/";
        }
        return "add-user";
    }

    /**
     * Function deleteUser finds the User that is up for deletion, deletes him from the service
     * and redirects you back to the page you were on now missing the user from the list.
     */
    @RequestMapping(value="/deleteuser/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") long id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedinuser");
        boolean isAdmin = userService.isAdmin(currentUser);
        if (isAdmin) {
            User user = userService.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid ID"));
            userService.delete(user);
            model.addAttribute("users", userService.findAll());
            return "redirect:/userlist";
        }
        return "redirect:/";
    }

    /**
     * Function loginForm, returns the page displaying a form to fill so you can login.
     */
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String loginForm(User user) {
        return "login";
    }

    /**
     * Function login, searches for your input in the userService and logs you in setting the loggedinuser
     * of the HttpSession. Then redirects you back to the welcome page.
     */
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(@Valid User user, BindingResult result, Model model, HttpSession session) {
        List<String> errors = new ArrayList<>();
        User exists = userService.findByUserame(user.getUsername());

        if (exists != null) {
            if (user.getPassword().equals(exists.getPassword())) {
                exists.setLastOnline();
                userService.save(exists);
                session.setAttribute("loggedinuser", exists);
                return "redirect:/";
            } else {
                errors.add("Incorrect password");
                model.addAttribute("errors",errors);
            }
        } else {
            errors.add("User not found");
            model.addAttribute("errors",errors);
        }
        return "login";
    }

    /**
     * Function logout, simply removes the attribute "loggedinuser" set onto the HttpSession during
     * login function.
     */
    @RequestMapping(value="/signout", method=RequestMethod.GET)
    public String logout(@Valid User user, BindingResult result, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("loggedinuser");
        if (sessionUser != null) {
            User currentUser = userService.findByUserame(sessionUser.getUsername());
            currentUser.setLastOnline();
            userService.save(currentUser);
            session.removeAttribute( "loggedinuser");
        }
        return "redirect:/";
    }

    /**
     * Function signupForm, returns the page holding the form to signup.
     */
    @RequestMapping(value="/signup", method = RequestMethod.GET)
    public String signupForm(User user) {
        return "signup";
    }

    /**
     * Function signup, sets the signed up user to the currently logged in user using 
     * by adding it to the HttpSession as an attribute and saves the newly signed up user to the 
     * userService.
     * Then redirects to the welcome page.
     */
    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public String signup(@Valid User user, BindingResult result, Model model, HttpSession session) {
        List<String> errors = new ArrayList<>();
        // getum baett inn fleiri villum med errors.add("....") osfrv
        if (userService.findByUserame(user.getUsername()) != null) {
            System.out.println("Username already exists");
            errors.add("Username already exists");
        }
        if (errors.size() == 0) {
            user.setRole("USER");
            user.setUserCreated();
            userService.save(user);
            session.setAttribute("loggedinuser", user);
            return "redirect:/";
        } else {
            System.out.println("errors");
            model.addAttribute("errors", errors);
            return "signup";
        }
    }
}

