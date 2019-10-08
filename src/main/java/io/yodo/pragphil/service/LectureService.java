package io.yodo.pragphil.service;

import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.User;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

import static io.yodo.pragphil.entity.RoleName.*;

public interface LectureService {

    List<Lecture> findAll();

    Lecture findById(int id);

    List<User> findLecturers();

    @Secured({ROLE_LECTURER, ROLE_ADMIN})
    void create(Lecture lecture);

    @Secured({ROLE_LECTURER, ROLE_ADMIN})
    void update(Lecture lecture);

    @Secured({ROLE_LECTURER, ROLE_ADMIN})
    void delete(Lecture lecture);

    @Secured({ROLE_LECTURER, ROLE_ADMIN, ROLE_STUDENT})
    List<User> findStudents(int lectureId);
}
