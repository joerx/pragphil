package io.yodo.pragphil.service;

import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.User;

import javax.transaction.Transactional;
import java.util.List;

public interface StudentService {

    List<Lecture> findAttendedLectures(int studentId);

    List<Lecture> findEligibleLectures(int studentId);

    User findStudentById(int id);

    Lecture findLectureById(int lectureId);

    void enroll(int userId, int lectureId);

    void delist(int studentId, int lectureId);
}
