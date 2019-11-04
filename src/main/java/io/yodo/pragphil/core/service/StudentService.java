package io.yodo.pragphil.core.service;

import io.yodo.pragphil.core.domain.entity.Lecture;
import io.yodo.pragphil.core.domain.entity.User;
import io.yodo.pragphil.core.domain.paging.Page;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface StudentService {

    @PreAuthorize("hasRole('ADMIN') or (hasRole('STUDENT') and principal.username == #student.username)")
    List<Lecture> findAttendedLectures(User student);

    @PreAuthorize("hasRole('ADMIN') or (hasRole('STUDENT') and principal.username == #student.username)")
    Page<Lecture> findAttendedLectures(User student, int pageNo);

    @PreAuthorize("hasRole('ADMIN') or (hasRole('STUDENT') and principal.username == #student.username)")
    Page<Lecture> findEligibleLectures(User student, int pageNo);

    @PostAuthorize("hasRole('ADMIN') or (hasRole('STUDENT') and principal.username == returnObject.username)")
    User findStudentById(int id);

    @PostAuthorize("hasRole('ADMIN') or (hasRole('STUDENT') and principal.username == #username)")
    User findStudentByUsername(String username);

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    Lecture findLectureById(int lectureId);

    @PreAuthorize("hasRole('ADMIN') or (hasRole('STUDENT') and principal.username == #student.username)")
    void enroll(User student, int lectureId);

    @PreAuthorize("hasRole('ADMIN') or (hasRole('STUDENT') and principal.username == #student.username)")
    void delist(User student, int lectureId);
}
