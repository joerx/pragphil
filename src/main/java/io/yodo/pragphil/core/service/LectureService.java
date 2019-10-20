package io.yodo.pragphil.core.service;

import io.yodo.pragphil.core.entity.Lecture;
import io.yodo.pragphil.core.entity.User;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface LectureService {

    @PreAuthorize("hasAnyRole('ADMIN', 'LECTURER')")
    List<Lecture> findAll();

    @PreAuthorize("hasRole('ADMIN') or (hasRole('LECTURER') and principal.username == #lecturer.username)")
    List<Lecture> findByLecturer(User lecturer);

    @PostAuthorize("hasRole('ADMIN') or (hasRole('LECTURER') and principal.username == returnObject.lecturer.username)")
    Lecture findById(int id);

    List<User> findLecturers();

    @PreAuthorize("hasAnyRole('ADMIN', 'LECTURER')")
    void create(Lecture lecture);

    @PreAuthorize("hasRole('ADMIN') or (hasRole('LECTURER') and principal.username == #lecture.lecturer.username)")
    void update(Lecture lecture);

    @PreAuthorize("hasRole('ADMIN') or (hasRole('LECTURER') and principal.username == #lecture.lecturer.username)")
    void delete(Lecture lecture);

    @PreAuthorize("hasAnyRole('ADMIN', 'LECTURER')")
    List<User> findStudents(int lectureId);

    @PreAuthorize("hasAnyRole('ADMIN', 'LECTURER')")
    User findLecturer(int userId);
}
