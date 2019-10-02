package io.yodo.pragphil.service;

import io.yodo.pragphil.dao.LectureDAO;
import io.yodo.pragphil.dao.UserDAO;
import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.User;
import io.yodo.pragphil.error.InvalidArgumentException;
import io.yodo.pragphil.error.NoSuchThingException;
import io.yodo.pragphil.error.IllegalAccessException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final UserDAO userDAO;

    private final LectureDAO lectureDAO;
    private final SessionFactory sessionFactory;

    public StudentServiceImpl(UserDAO userDAO, LectureDAO lectureDAO, SessionFactory sessionFactory) {
        this.userDAO = userDAO;
        this.lectureDAO = lectureDAO;
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<User> findAllStudents() {
        return userDAO.findByRole("ROLE_STUDENT");
    }

    @Override
    @Transactional
    public List<Lecture> findAttendedLectures(int studentId) {
        return lectureDAO.findAttendedLectures(studentId);
    }


    @Override
    @Transactional
    public List<Lecture> findAllLectures() {
        return lectureDAO.findAll();
    }

    @Override
    @Transactional
    public List<Lecture> findEligibleLectures(int studentId) {
        final User student = userDAO.findById(studentId);
        List<Lecture> lectures = lectureDAO.findAll();

        return lectures.stream()
                .filter(l -> !student.isEnrolledIn(l) && l.getLecturer() != student)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User findStudentById(int id) {
        return userDAO.findByIdWithLectures(id);
    }

    @Override
    @Transactional
    public Lecture findLectureById(int lectureId) {
        return lectureDAO.findById(lectureId);
    }

    @Override
    @Transactional
    public void enroll(int studentId, int lectureId) {
        User u = mustFindUser(studentId);
        Lecture l = mustFindLecture(lectureId);

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
    public void delist(int studentId, int lectureId) {
        User u = mustFindUser(studentId);
        Lecture l = mustFindLecture(lectureId);

        if (!l.getStudents().contains(u)) {
            throw new IllegalStateException("Student is not enrolled in this course");
        }

        u.delist(l);
        userDAO.update(u);
    }

    private User mustFindUser(int userId) {
        User u = userDAO.findById(userId);
        if (u == null) {
            throw new NoSuchThingException("No user with id " + userId);
        }
        return u;
    }

    private Lecture mustFindLecture(int lectureId) {
        Lecture l = lectureDAO.findById(lectureId);
        if (l == null) {
            throw new NoSuchThingException("No lecture with id " + lectureId);
        }
        return l;
    }
}
