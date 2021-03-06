package io.yodo.pragphil.core.service;

import io.yodo.pragphil.core.domain.dao.LectureDAO;
import io.yodo.pragphil.core.domain.dao.UserDAO;
import io.yodo.pragphil.core.domain.entity.Lecture;
import io.yodo.pragphil.core.domain.entity.User;
import io.yodo.pragphil.core.domain.paging.Page;
import io.yodo.pragphil.core.error.InvalidArgumentException;
import io.yodo.pragphil.core.error.NoSuchThingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final UserDAO userDAO;

    private final LectureDAO lectureDAO;

    private final int recordsPerPage;

    public StudentServiceImpl(
            UserDAO userDAO,
            LectureDAO lectureDAO,
            @Value("${paging.recordsPerPage}") int recordsPerPage
    ) {
        this.userDAO = userDAO;
        this.lectureDAO = lectureDAO;
        this.recordsPerPage = recordsPerPage;
    }

    @Override
    @Transactional
    public List<Lecture> findAttendedLectures(User student) {
        return lectureDAO.findAttendedLectures(student.getId());
    }

    @Override
    @Transactional
    public Page<Lecture> findAttendedLectures(User student, int pageNo) {
        return lectureDAO.findAttendedLectures(student.getId(), pageNo, recordsPerPage);
    }

    @Override
    @Transactional
    public Page<Lecture> findEligibleLectures(User student, int pageNo) {
        return lectureDAO.findEligibleLectures(student.getId(), pageNo, recordsPerPage);
//        User u = findStudentById(student.getId());
//        List<Lecture> lectures = lectureDAO.findAll();
//
//        return lectures.stream()
//                .filter(l -> !u.isEnrolledIn(l) && l.getLecturer() != student)
//                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User findStudentById(int id) {
        return userDAO.findById(id);
    }

    @Override
    @Transactional
    public User findStudentByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    @Transactional
    public Lecture findLectureById(int lectureId) {
        return lectureDAO.findById(lectureId);
    }

    @Override
    @Transactional
    public void enroll(User student, int lectureId) {
        Lecture l = mustFindLecture(lectureId);
        User u = findStudentById(student.getId());

        if (l.getLecturer() == u) {
            throw new InvalidArgumentException("Lecturer can't enroll in their own lecture");
        }
        if (!u.isStudent()) {
            throw new InvalidArgumentException("Only students can enroll in lectures");
        }
        if (l.getStudents().contains(u)) {
            throw new IllegalStateException("Student is already enrolled in this course");
        }

        u.enroll(l);
        userDAO.update(u);
    }

    @Override
    @Transactional
    public void delist(User student, int lectureId) {
        Lecture l = mustFindLecture(lectureId);
        User u = findStudentById(student.getId());

        if (!l.getStudents().contains(u)) {
            throw new IllegalStateException("Student is not enrolled in this course");
        }

        u.delist(l);
        userDAO.update(u);
    }

    private Lecture mustFindLecture(int lectureId) {
        Lecture l = lectureDAO.findById(lectureId);
        if (l == null) {
            throw new NoSuchThingException("No lecture with id " + lectureId);
        }
        return l;
    }
}
