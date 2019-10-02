package io.yodo.pragphil.controller;

import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.User;
import io.yodo.pragphil.service.StudentService;
import io.yodo.pragphil.view.helper.FlashHelper;
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

    private String STUDENTS_VIEW_PATH_TPL = "/students/view/%s";

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(value = "/view/{studentId}", method = RequestMethod.GET)
    public String view(@PathVariable int studentId, Model model) {
        User student = studentService.findStudentById(studentId);
        List<Lecture> lectures = studentService.findEligibleLectures(studentId);
        List<Lecture> attendedLectures = studentService.findAttendedLectures(studentId);

        model.addAttribute("student", student);
        model.addAttribute("attendedLectures", attendedLectures);
        model.addAttribute("lectures", lectures);

        return "students/view";
    }

    @RequestMapping(value = "/enroll/{studentId}", method = RequestMethod.GET)
    public String enroll(
            @PathVariable int studentId,
            @RequestParam(name = "l") int lectureId,
            Model model,
            RedirectAttributes rs
    ) {
        studentService.enroll(studentId, lectureId);

        User student = studentService.findStudentById(studentId);
        Lecture lecture = studentService.findLectureById(lectureId);
        FlashHelper.setInfo(rs, "Student " + student.getUsername() + " enrolled in " + lecture.getName());

        return "redirect:" + String.format(STUDENTS_VIEW_PATH_TPL, studentId);
    }

    @RequestMapping(value = "/delist/{studentId}", method = RequestMethod.GET)
    public String delist(
            @PathVariable int studentId,
            @RequestParam(name = "l") int lectureId,
            Model model,
            RedirectAttributes rs
    ) {
        studentService.delist(studentId, lectureId);

        User student = studentService.findStudentById(studentId);
        Lecture lecture = studentService.findLectureById(lectureId);
        FlashHelper.setInfo(rs, "Student " + student.getUsername() + " delisted from in " + lecture.getName());

        return "redirect:" + String.format(STUDENTS_VIEW_PATH_TPL, studentId);
    }
}
