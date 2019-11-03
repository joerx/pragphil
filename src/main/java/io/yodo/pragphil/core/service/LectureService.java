package io.yodo.pragphil.core.service;

import io.yodo.pragphil.core.domain.entity.Lecture;
import io.yodo.pragphil.core.domain.entity.User;
import io.yodo.pragphil.core.domain.paging.Page;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface LectureService {

    @PreAuthorize("isAuthenticated()")
    List<Lecture> findAll();

    @PreAuthorize("isAuthenticated()")
    Page<Lecture> findOnPage(int pageNo);

    @PreAuthorize("isAuthenticated()")
    Page<Lecture> findByLecturer(User lecturer, int pageNo);

    @PostAuthorize("isAuthenticated()")
    Lecture findById(int id);

    @PostAuthorize("isAuthenticated()")
    List<User> findLecturers();

    @PreAuthorize("hasAnyRole('ADMIN') or (hasRole('LECTURER') and principal.username == #lecture.lecturer.username)")
    void create(Lecture lecture);

    @PreAuthorize("hasRole('ADMIN') or (hasRole('LECTURER') and principal.username == #lecture.lecturer.username)")
    void update(Lecture lecture);

    @PreAuthorize("hasRole('ADMIN') or (hasRole('LECTURER') and principal.username == #lecture.lecturer.username)")
    void delete(Lecture lecture);

    @PreAuthorize("hasAnyRole('ADMIN') or (hasRole('LECTURER') and principal.username == #lecture.lecturer.username)")
    List<User> findStudents(Lecture lecture);

    @PostAuthorize("isAuthenticated()")
    User findLecturer(int userId);

    @PostAuthorize("isAuthenticated()")
    User findLecturer(String username);
}
