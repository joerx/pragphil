package io.yodo.pragphil.dao;

import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.User;

import java.util.List;

public interface LectureDAO {

    List<Lecture> findAll();

    Lecture findById(int id);

    List<Lecture> findAttendedLectures(int studentId);

    void create(Lecture l);

    void update(Lecture l);

    void delete(Lecture l);

    List<User> findStudents(int lectureId);

    List<Lecture> findByLecturer(int userId);
}
