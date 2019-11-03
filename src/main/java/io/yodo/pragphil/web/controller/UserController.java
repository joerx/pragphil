package io.yodo.pragphil.web.controller;

import io.yodo.pragphil.core.domain.entity.Lecture;
import io.yodo.pragphil.core.domain.entity.Role;
import io.yodo.pragphil.core.domain.entity.User;
import io.yodo.pragphil.core.domain.paging.Page;
import io.yodo.pragphil.core.error.NoSuchThingException;
import io.yodo.pragphil.core.service.LectureService;
import io.yodo.pragphil.core.service.UserService;
import io.yodo.pragphil.core.tracing.NoTrace;
import io.yodo.pragphil.core.util.RandomTokenGenerator;
import io.yodo.pragphil.web.binding.UserRoleEditor;
import io.yodo.pragphil.web.view.helper.FlashHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final LectureService lectureService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String DEFAULT_REDIRECT_LOCATION = "/users/list";

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, LectureService lectureService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.lectureService = lectureService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "redirect:/users/list";
    }

    @InitBinder
    @NoTrace
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

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String viewUser(
            @PathVariable String username,
            @RequestParam(defaultValue = "1") int lp,
            Model model
    ) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new NoSuchThingException("No such user: " + username);
        }

        Page<Lecture> lectures = lectureService.findByLecturer(user, lp);

        model.addAttribute("user", user);
        model.addAttribute("lectures", lectures);
        return "users/view";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newUser(Model model) {
        User u = new User();
        u.setApiToken(RandomTokenGenerator.generateRandomToken());
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
        return "redirect:/users/" + user.getUsername();
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
            prepareUserForm(model, user);
            return "users/edit";
        }

        // if password changes, encode new password
        if (user.getPassword() != null && user.getPassword().length() > 0) {
            user.setPassword(encodePassword(user.getPassword()));
        }

        userService.update(user);

        FlashHelper.setInfo(ra, "User " + user.getUsername() + " updated");
        return "redirect:/users/" + user.getUsername();
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
