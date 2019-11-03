package io.yodo.pragphil.core.domain.dao;

import io.yodo.pragphil.core.domain.entity.Lecture;
import io.yodo.pragphil.core.domain.entity.User;
import io.yodo.pragphil.core.domain.paging.Page;

import java.util.List;

public interface LectureDAO {

    List<Lecture> findAll();

    Page<Lecture> findOnPage(int pageNo, int numRecords);

    Page<Lecture> findByLecturer(int userId, int pageNo, int numRecords);

    Lecture findById(int id);

    List<Lecture> findAttendedLectures(int studentId);

    void create(Lecture l);

    void update(Lecture l);

    void delete(Lecture l);

    List<User> findStudents(int lectureId);
}
