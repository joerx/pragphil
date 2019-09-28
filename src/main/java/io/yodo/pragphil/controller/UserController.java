package io.yodo.pragphil.controller;

import io.yodo.pragphil.entity.User;
import io.yodo.pragphil.error.NoSuchThingException;
import io.yodo.pragphil.service.UserService;
import io.yodo.pragphil.view.helper.FlashHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final Logger log = Logger.getLogger(getClass().getName());

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "redirect:/users/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String viewUser(@PathVariable int id, Model model) {
        User user = userService.findById(id);

        if (user == null) throw new NoSuchThingException("No user with id " + id);

        model.addAttribute("user", user);
        return "users/view";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newUser(Model model) {
        User u = new User();
        u.setEnabled(true);
        model.addAttribute("user", u);
        return "users/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createUser(
            @ModelAttribute User user,
            BindingResult binding,
            Model model,
            RedirectAttributes ra
    ) {
        if (binding.hasErrors()) {
            model.addAttribute("user", user);
            return "users/new";
        }

        userService.create(user);

        FlashHelper.setInfo(ra, "User created");
        return "redirect:/users/view/" + user.getId();
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUser(@PathVariable int id, Model model) {
        User u = userService.findById(id);

        if (u == null) throw new NoSuchThingException("No user with id " + id);

        u.setPassword(null);
        model.addAttribute("user", u);

        return "users/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateUser(
            @ModelAttribute User user,
            BindingResult binding,
            Model model,
            RedirectAttributes ra
    ) {
        if (binding.hasErrors()) {
            model.addAttribute("user", user);
            return "users/edit";
        }
        userService.update(user);

        FlashHelper.setInfo(ra, "User created");
        return "redirect:/users/view/" + user.getId();
    }

    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disableUser(@PathVariable int id, HttpServletRequest req, RedirectAttributes ra) {
        User u = userService.findById(id);

        if (u == null) throw new NoSuchThingException("No user with id " + id);

        userService.disableUser(u);

        FlashHelper.setInfo(ra, "User disabled");
        return redirectToReferrer(req, "/users/list");
    }

    @RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
    public String enableUser(@PathVariable int id, HttpServletRequest req, RedirectAttributes ra) {
        User u = userService.findById(id);

        if (u == null) throw new NoSuchThingException("No user with id " + id);

        userService.enableUser(u);

        FlashHelper.setInfo(ra,"User enabled");
        return redirectToReferrer(req, "/users/list");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable int id, RedirectAttributes ra) {
        User u = userService.findById(id);

        if (u == null) throw new NoSuchThingException("No user with id " + id);

        userService.delete(u);

        FlashHelper.setInfo(ra, "User deleted");
        return "redirect:/users/list";
    }

    private String redirectToReferrer(HttpServletRequest req, String defaultLocation) {
        String referrer = req.getHeader("referer");
        log.info("Got referrer " + referrer);

        if (referrer == null) {
            return "redirect:" + defaultLocation;
        } else {
            return "redirect:" + referrer;
        }
    }
}
