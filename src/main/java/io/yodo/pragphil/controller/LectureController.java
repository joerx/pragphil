package io.yodo.pragphil.controller;

import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.RoleName;
import io.yodo.pragphil.entity.User;
import io.yodo.pragphil.error.AccessDeniedException;
import io.yodo.pragphil.error.NoSuchThingException;
import io.yodo.pragphil.security.PrincipalHelper;
import io.yodo.pragphil.service.LectureService;
import io.yodo.pragphil.view.helper.FlashHelper;
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
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/lectures")
public class LectureController {

    private final LectureService lectureService;

    private Logger log = Logger.getLogger(getClass().getName());

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new IdToLecturerFormatter(lectureService), User.class);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String index() {
        return "redirect:/lectures/list";
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String findLectures(Model model, Authentication auth) {
        User user = PrincipalHelper.getUser(auth);
        List<Lecture> lectures = getLecturesForUser(user);

        model.addAttribute("lectures", lectures);
        return "lectures/list";
    }

    @RequestMapping(path = "/view/{id}", method = RequestMethod.GET)
    public String showLecture(@PathVariable int id, Model model) {
        Lecture lecture = lectureService.findById(id);
        List<User> students = lectureService.findStudents(id);

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
            log.warning("binding has errors " + binding);
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
            log.warning("binding has errors " + binding);
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
        List<User> students = lectureService.findStudents(lecture.getId());
        List<User> lecturers = lectureService.findLecturers();

        // students can't be lecturers for the same lecture
        List<User> result = lecturers.stream()
                .filter(u -> !students.contains(u))
                .collect(Collectors.toList());

        model.addAttribute("lecturers", result);
        model.addAttribute("lecture", lecture);
    }

    private List<Lecture> getLecturesForUser(User user) {
        if (user.hasRole(RoleName.ROLE_ADMIN)) {
            return lectureService.findAll();
        }
        if (user.hasRole(RoleName.ROLE_LECTURER)) {
            return lectureService.findByLecturer(user);
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
