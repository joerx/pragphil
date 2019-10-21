package io.yodo.pragphil.api.resource.mapper;

import io.yodo.pragphil.api.resource.EnrollmentResource;
import io.yodo.pragphil.api.resource.LectureResource;
import io.yodo.pragphil.api.resource.StudentResource;
import io.yodo.pragphil.core.entity.Lecture;
import io.yodo.pragphil.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentMapper {

    private final LectureMapper lectureMapper;

    @Autowired
    public StudentMapper(LectureMapper lectureMapper) {
        this.lectureMapper = lectureMapper;
    }

    public StudentResource toResource(User student) {
        StudentResource sr = new StudentResource();
        sr.setUsername(student.getUsername());
        sr.setId(student.getId());

        return sr;
    }

    public StudentResource toResource(User student, List<Lecture> attendedLectures) {
        List<LectureResource> lectures = attendedLectures.stream()
                .map(lectureMapper::toResource)
                .collect(Collectors.toList());

        StudentResource sr = toResource(student);
        sr.setAttendedLectures(lectures);

        return sr;
    }

    public EnrollmentResource toEnrollment(User student, Lecture lecture) {
        EnrollmentResource e = new EnrollmentResource();
        e.setStudentName(student.getUsername());
        e.setLectureId(lecture.getId());
        e.setLectureName(lecture.getName());
        return e;
    }
}
