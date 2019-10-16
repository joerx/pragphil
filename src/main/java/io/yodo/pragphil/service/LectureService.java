package io.yodo.pragphil.service;

import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.User;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface LectureService {

    @PreAuthorize("@lectureAccess.canList(principal)")
    List<Lecture> findAll();

    @PreAuthorize("@lectureAccess.canList(principal)")
    List<Lecture> findByLecturer(User lecturer);

    @PostAuthorize("@lectureAccess.isAdmin(principal, returnObject)")
    Lecture findById(int id);

    List<User> findLecturers();

    @PreAuthorize("@lectureAccess.canList(principal)")
    void create(Lecture lecture);

    @PreAuthorize("@lectureAccess.isAdmin(principal, #lecture)")
    void update(Lecture lecture);

    @PreAuthorize("@lectureAccess.isAdmin(principal, #lecture)")
    void delete(Lecture lecture);

    @PreAuthorize("@lectureAccess.canList(principal)")
    List<User> findStudents(int lectureId);

    @PreAuthorize("@lectureAccess.canList(principal)")
    User findLecturer(int userId);
}
