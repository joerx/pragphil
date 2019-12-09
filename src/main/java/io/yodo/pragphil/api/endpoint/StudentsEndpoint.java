package io.yodo.pragphil.api.endpoint;

import io.yodo.pragphil.api.resource.EnrollmentResource;
import io.yodo.pragphil.api.resource.StudentResource;
import io.yodo.pragphil.api.resource.mapper.LectureMapper;
import io.yodo.pragphil.api.resource.mapper.StudentMapper;
import io.yodo.pragphil.core.domain.entity.Lecture;
import io.yodo.pragphil.core.domain.entity.User;
import io.yodo.pragphil.core.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class StudentsEndpoint {

    private final StudentService studentService;

    private final StudentMapper studentMapper;

    @Autowired
    public StudentsEndpoint(StudentService studentService, StudentMapper studentMapper, LectureMapper lectureMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @GetMapping("/students/{username}")
    public StudentResource getStudent(@PathVariable String username) {
        User student = studentService.findStudentByUsername(username);
        List<Lecture> lectures = studentService.findAttendedLectures(student);

        return studentMapper.toResource(student, lectures);
    }

    @PostMapping("/students/{username}/lectures")
    public EnrollmentResource enroll(@PathVariable String username, @RequestBody EnrollmentResource enrollment) {
        User student = studentService.findStudentByUsername(username);
        Lecture lecture = studentService.findLectureById(enrollment.getLectureId());

        studentService.enroll(student, enrollment.getLectureId());

        return studentMapper.toEnrollment(student, lecture);
    }

    @DeleteMapping("/students/{username}/lectures/{lectureId}")
    public EnrollmentResource delist(@PathVariable String username, @PathVariable int lectureId) {
        User student = studentService.findStudentByUsername(username);
        Lecture lecture = studentService.findLectureById(lectureId);

        studentService.delist(student, lectureId);

        return studentMapper.toEnrollment(student, lecture);
    }
}
