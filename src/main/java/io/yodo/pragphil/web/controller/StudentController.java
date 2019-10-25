package io.yodo.pragphil.web.controller;

import io.yodo.pragphil.core.entity.Lecture;
import io.yodo.pragphil.core.entity.User;
import io.yodo.pragphil.core.service.StudentService;
import io.yodo.pragphil.web.view.helper.FlashHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(path = "/students")
public class StudentController {

    private final StudentService studentService;

    private String STUDENTS_VIEW_PATH_TPL = "/students/%s";

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String view(@PathVariable String username, Model model) {
        User student = studentService.findStudentByUsername(username);

        List<Lecture> lectures = studentService.findEligibleLectures(student);
        List<Lecture> attendedLectures = studentService.findAttendedLectures(student);

        model.addAttribute("student", student);
        model.addAttribute("attendedLectures", attendedLectures);
        model.addAttribute("lectures", lectures);

        return "students/view";
    }

    @RequestMapping(value = "/enroll/{studentId}", method = RequestMethod.GET)
    public String enroll(
            @PathVariable int studentId,
            @RequestParam(name = "l") int lectureId,
            RedirectAttributes rs
    ) {
        User student = studentService.findStudentById(studentId);
        studentService.enroll(student, lectureId);

        Lecture lecture = studentService.findLectureById(lectureId);
        FlashHelper.setInfo(rs, "Student " + student.getUsername() + " enrolled in " + lecture.getName());

        return "redirect:" + String.format(STUDENTS_VIEW_PATH_TPL, student.getUsername());
    }

    @RequestMapping(value = "/delist/{studentId}", method = RequestMethod.GET)
    public String delist(
            @PathVariable int studentId,
            @RequestParam(name = "l") int lectureId,
            RedirectAttributes rs
    ) {
        User student = studentService.findStudentById(studentId);
        studentService.delist(student, lectureId);

        Lecture lecture = studentService.findLectureById(lectureId);
        FlashHelper.setInfo(rs, "Student " + student.getUsername() + " delisted from in " + lecture.getName());

        return "redirect:" + String.format(STUDENTS_VIEW_PATH_TPL, student.getUsername());
    }
}