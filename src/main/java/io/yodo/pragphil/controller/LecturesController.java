package io.yodo.pragphil.controller;

import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.error.NoSuchThingException;
import io.yodo.pragphil.service.LecturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(path = "/lectures")
public class LecturesController {

    private final LecturesService lecturesService;

    @Autowired
    public LecturesController(LecturesService lecturesService) {
        this.lecturesService = lecturesService;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String index() {
        return "redirect:/lectures/list";
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String findLectures(Model model) {
        List<Lecture> lectures = lecturesService.findAll();
        model.addAttribute("lectures", lectures);
        return "lectures/list";
    }

    @RequestMapping(path = "/view/{id}", method = RequestMethod.GET)
    public String showLecture(@PathVariable int id, Model model) {
        Lecture lecture = lecturesService.findById(id);

        if (lecture == null) throw new NoSuchThingException("No lecture with id " + id);

        model.addAttribute("lecture", lecture);
        return "lectures/view";
    }

    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public String newLecture(Model model) {
        Lecture lecture = new Lecture();
        model.addAttribute("lecture", lecture);
        return "lectures/new";
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String createLecture(
            @ModelAttribute Lecture lecture,
            BindingResult binding,
            Model model,
            RedirectAttributes r
    ) {
        if (binding.hasErrors()) {
            model.addAttribute("lecture", lecture);
            return "lectures/new";
        }
        lecturesService.create(lecture);
        return "redirect:/lectures/view/" + lecture.getId();
    }

    @RequestMapping(path = "/edit/{id}", method = RequestMethod.GET)
    public String editLecture(@PathVariable int id, Model model) {
        Lecture lecture = lecturesService.findById(id);

        if (lecture == null) throw new NoSuchThingException("No lecture with id " + id);

        model.addAttribute("lecture", lecture);
        return "lectures/edit";
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String updateLecture(
            @ModelAttribute Lecture lecture,
            BindingResult binding,
            Model model,
            RedirectAttributes r
    ) {
        if (binding.hasErrors()) {
            model.addAttribute("lecture", lecture);
            return "lectures/edit";
        }
        lecturesService.update(lecture);
        return "redirect:/lectures/view/" + lecture.getId();
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.GET)
    public String deleteLecture(@PathVariable int id) {
        Lecture lecture = lecturesService.findById(id);

        if (lecture == null) throw new NoSuchThingException("No lecture with id " + id);

        lecturesService.delete(lecture);
        return "redirect:/lectures/list";
    }
}
