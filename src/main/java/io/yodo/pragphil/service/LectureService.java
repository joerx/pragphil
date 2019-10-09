package io.yodo.pragphil.service;

import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.User;
import org.jboss.logging.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;

import javax.transaction.Transactional;
import java.util.List;

public interface LectureService {

    @PreAuthorize("@lectureACL.canList(principal)")
    List<Lecture> findAll();

    @PreAuthorize("@lectureACL.canList(principal)")
    List<Lecture> findByLecturer(User lecturer);

    @PostAuthorize("@lectureACL.isAdmin(returnObject, principal)")
    Lecture findById(int id);

    List<User> findLecturers();

    @PreAuthorize("@lectureACL.canList(principal)")
    void create(Lecture lecture);

    @PreAuthorize("@lectureACL.isAdmin(#lecture, principal)")
    void update(Lecture lecture);

    @PreAuthorize("@lectureACL.isAdmin(#lecture, principal)")
    void delete(Lecture lecture);

    @PreAuthorize("@lectureACL.canList(principal)")
    List<User> findStudents(int lectureId);

    @PreAuthorize("@lectureACL.canList(principal)")
    User findLecturer(int userId);
}
