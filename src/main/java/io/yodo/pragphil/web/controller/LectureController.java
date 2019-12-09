package io.yodo.pragphil.web.controller;

import io.yodo.pragphil.core.domain.entity.Lecture;
import io.yodo.pragphil.core.domain.entity.RoleName;
import io.yodo.pragphil.core.domain.entity.User;
import io.yodo.pragphil.core.domain.paging.Page;
import io.yodo.pragphil.core.error.AccessDeniedException;
import io.yodo.pragphil.core.error.NoSuchThingException;
import io.yodo.pragphil.core.service.LectureService;
import io.yodo.pragphil.core.service.UserService;
import io.yodo.pragphil.core.tracing.NoTrace;
import io.yodo.pragphil.web.view.helper.FlashHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/lectures")
public class LectureController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final LectureService lectureService;

    private final UserService userService;

    @Autowired
    public LectureController(LectureService lectureService, UserService userService) {
        this.lectureService = lectureService;
        this.userService = userService;
    }

    @InitBinder
    @NoTrace
    public void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new IdToLecturerFormatter(lectureService), User.class);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    @NoTrace
    public String index() {
        return "redirect:/lectures/list";
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String findLectures(@RequestParam(defaultValue = "1") int p, Model model, Authentication auth) {
        log.debug("Finding lectures for user " + auth);
        User user = userService.findByUsername(auth.getName());

        Page<Lecture> lectures = getLecturesForUser(user, p);
        model.addAttribute("lectures", lectures);

        return "lectures/list";
    }

    @RequestMapping(path = "/view/{id}", method = RequestMethod.GET)
    public String showLecture(@PathVariable int id, Model model) {
        Lecture lecture = lectureService.findById(id);
        List<User> students = lectureService.findStudents(lecture);

        if (lecture == null) throw new NoSuchThingException("No lecture with id " + id);

        model.addAttribute("lecture", lecture);
        model.addAttribute("students", students);

        return "lectures/view";
    }

    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newLecture(Model model) {
        prepareForm(model, new Lecture());
        return "lectures/new";
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String createLecture(
            @Valid @ModelAttribute Lecture lecture,
            BindingResult binding,
            Model model,
            RedirectAttributes r
    ) {
        if (binding.hasErrors()) {
            prepareForm(model, lecture);
            return "lectures/new";
        }
        lectureService.create(lecture);

        FlashHelper.setInfo(r, "Lecture created");
        return "redirect:/lectures/view/" + lecture.getId();
    }

    @RequestMapping(path = "/edit/{id}", method = RequestMethod.GET)
    public String editLecture(@PathVariable int id, Model model) {
        Lecture lecture = lectureService.findById(id);

        if (lecture == null) throw new NoSuchThingException("No lecture with id " + id);

        prepareForm(model, lecture);
        return "lectures/edit";
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String updateLecture(
            @Valid @ModelAttribute Lecture lecture,
            BindingResult binding,
            Model model,
            RedirectAttributes r
    ) {
        if (binding.hasErrors()) {
            prepareForm(model, lecture);
            return "lectures/edit";
        }

        lectureService.update(lecture);

        FlashHelper.setInfo(r, "Lecture updated");
        return "redirect:/lectures/view/" + lecture.getId();
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.GET)
    public String deleteLecture(@PathVariable int id, RedirectAttributes r) {
        Lecture lecture = lectureService.findById(id);

        if (lecture == null) throw new NoSuchThingException("No lecture with id " + id);

        lectureService.delete(lecture);

        FlashHelper.setInfo(r, "Lecture deleted");
        return "redirect:/lectures/list";
    }

    private void prepareForm(Model model, Lecture lecture) {
        List<User> students = lectureService.findStudents(lecture);
        List<User> lecturers = lectureService.findLecturers();

        // students can't be lecturers for the same lecture
        List<User> result = lecturers.stream()
                .filter(u -> !students.contains(u))
                .collect(Collectors.toList());

        model.addAttribute("lecturers", result);
        model.addAttribute("lecture", lecture);
    }

    private Page<Lecture> getLecturesForUser(User user, int pageNo) {
        if (user.hasRole(RoleName.ROLE_ADMIN)) {
            return lectureService.findOnPage(pageNo);
        }
        if (user.hasRole(RoleName.ROLE_LECTURER)) {
            return lectureService.findByLecturer(user, pageNo);
        }
        throw new AccessDeniedException("User is not allowed to view this");
    }

    private static class IdToLecturerFormatter implements Formatter<User> {

        private final LectureService lectureService;

        IdToLecturerFormatter(LectureService lectureService) {
            this.lectureService = lectureService;
        }

        @Override
        public User parse(String s, Locale locale) {
            if (s.equals("") || s.equals("0")) return null;
            int userId = Integer.parseInt(s);
            return lectureService.findLecturer(userId);
        }

        @Override
        public String print(User user, Locale locale) {
            return Integer.toString(user.getId());
        }
    }
}
