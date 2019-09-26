package io.yodo.pragphil.controller;

import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.service.LecturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(path = "/lectures")
public class LecturesController {

    private final LecturesService lecturesService;

    @Autowired
    public LecturesController(LecturesService lecturesService) {
        this.lecturesService = lecturesService;
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public String findLectures(Model model) {
        List<Lecture> lectures = lecturesService.findAll();
        model.addAttribute("lectures", lectures);
        return "lectures/list";
    }

    @RequestMapping(path = "/view/{id}")
    public String showLecture(@PathVariable int id, Model model) {
        Lecture lecture = lecturesService.findById(id);
        model.addAttribute("lecture", lecture);
        return "lectures/view";
    }
}
