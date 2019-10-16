package io.yodo.pragphil.service;

import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.RoleName;
import io.yodo.pragphil.entity.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.transaction.Transactional;
import java.util.List;

import static io.yodo.pragphil.entity.RoleName.*;

public interface StudentService {

    @PreAuthorize("@studentAccess.isAdmin(principal, #studentId)")
    List<Lecture> findAttendedLectures(int studentId);

    @PreAuthorize("@studentAccess.isAdmin(principal, #studentId)")
    List<Lecture> findEligibleLectures(int studentId);

    @PostAuthorize("@studentAccess.isAdmin(principal, returnObject.id)")
    User findStudentById(int id);

    @PreAuthorize("@studentAccess.canList(principal)")
    Lecture findLectureById(int lectureId);

    @PreAuthorize("@studentAccess.isAdmin(principal, #studentId)")
    void enroll(int studentId, int lectureId);

    @PreAuthorize("@studentAccess.isAdmin(principal, #studentId)")
    void delist(int studentId, int lectureId);
}
