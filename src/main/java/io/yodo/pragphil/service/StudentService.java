package io.yodo.pragphil.service;

import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.RoleName;
import io.yodo.pragphil.entity.User;
import org.springframework.security.access.annotation.Secured;

import javax.transaction.Transactional;
import java.util.List;

import static io.yodo.pragphil.entity.RoleName.*;

@Secured({ROLE_STUDENT, ROLE_ADMIN})
public interface StudentService {


    List<Lecture> findAttendedLectures(int studentId);

    List<Lecture> findEligibleLectures(int studentId);

    User findStudentById(int id);

    Lecture findLectureById(int lectureId);

    void enroll(int studentId, int lectureId);

    void delist(int studentId, int lectureId);
}
