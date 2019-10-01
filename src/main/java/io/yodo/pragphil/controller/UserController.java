package io.yodo.pragphil.controller;

import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.Role;
import io.yodo.pragphil.entity.User;
import io.yodo.pragphil.error.NoSuchThingException;
import io.yodo.pragphil.service.UserService;
import io.yodo.pragphil.view.helper.FlashHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final Logger log = Logger.getLogger(getClass().getName());

    private static final String DEFAULT_REDIRECT_LOCATION = "/users/list";

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "redirect:/users/list";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Role.class, "roles", new UserRoleEditor(userService));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listUsers(@RequestParam(required=false) String role, Model model) {
        List<User> users;
        if (role == null) {
            users = userService.findAll();
        } else {
            users = userService.findByRole(role);
        }
        model.addAttribute("users", users);
        return "users/list";
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String viewUser(@PathVariable int id, Model model) {
        User user = userService.findByIdWithLectures(id);
        if (user == null) throw new NoSuchThingException("No user with id " + id);
        model.addAttribute("user", user);
        return "users/view";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newUser(Model model) {
        User u = new User();
        u.setEnabled(true);

        prepareUserForm(model, u);
        return "users/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createUser(
            @Valid @ModelAttribute User user,
            BindingResult binding,
            Model model,
            RedirectAttributes ra
    ) {
        if (binding.hasErrors()) {
            prepareUserForm(model, user);
            return "users/new";
        }

        log.info("Creating user with roles " + user.getRoles());

        user.setPassword(encodePassword(user.getPassword()));
        userService.create(user);

        FlashHelper.setInfo(ra, "User " + user.getUsername() + " created (" + user.getId() + ")");
        return "redirect:/users/view/" + user.getId();
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUser(@PathVariable int id, Model model) {
        User u = userService.findById(id);

        if (u == null) throw new NoSuchThingException("No user with id " + id);
        u.setPassword(null);

        prepareUserForm(model, u);
        return "users/edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateUser(
            @Valid @ModelAttribute User user,
            BindingResult binding,
            Model model,
            RedirectAttributes ra
    ) {
        log.info("Updating user with roles " + user.getRoles());

        if (binding.hasErrors()) {
            log.warning("Binding errors" + binding.getAllErrors());
            prepareUserForm(model, user);
            return "users/edit";
        }

        // if password changes, encode new password
        if (user.getPassword() != null && user.getPassword().length() > 0) {
            user.setPassword(encodePassword(user.getPassword()));
        }

        userService.update(user);

        FlashHelper.setInfo(ra, "User " + user.getUsername() + " updated");
        return "redirect:/users/view/" + user.getId();
    }

    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disableUser(@PathVariable int id, HttpServletRequest req, RedirectAttributes ra) {
        User u = userService.findById(id);

        if (u == null) throw new NoSuchThingException("No user with id " + id);

        userService.disableUser(u);

        FlashHelper.setInfo(ra, "User disabled");
        return redirectToReferrer(req);
    }

    @RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
    public String enableUser(@PathVariable int id, HttpServletRequest req, RedirectAttributes ra) {
        User u = userService.findById(id);

        if (u == null) throw new NoSuchThingException("No user with id " + id);

        userService.enableUser(u);

        FlashHelper.setInfo(ra,"User enabled");
        return redirectToReferrer(req);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable int id, RedirectAttributes ra) {
        User u = userService.findById(id);

        if (u == null) throw new NoSuchThingException("No user with id " + id);

        userService.delete(u);

        FlashHelper.setInfo(ra, "User deleted");
        return "redirect:/users/list";
    }

    private String redirectToReferrer(HttpServletRequest req) {
        String referrer = req.getHeader("referer");
        log.info("Got referrer " + referrer);

        if (referrer == null) {
            return "redirect:" + DEFAULT_REDIRECT_LOCATION;
        } else {
            return "redirect:" + referrer;
        }
    }

    private void prepareUserForm(Model model, User u) {
        List<Role> allRoles = userService.getAllRoles();
        model.addAttribute("user", u);
        model.addAttribute("allRoles", allRoles);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
