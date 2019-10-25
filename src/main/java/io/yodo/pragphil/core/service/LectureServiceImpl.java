package io.yodo.pragphil.core.service;

import io.yodo.pragphil.core.dao.LectureDAO;
import io.yodo.pragphil.core.dao.UserDAO;
import io.yodo.pragphil.core.entity.Lecture;
import io.yodo.pragphil.core.entity.RoleName;
import io.yodo.pragphil.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LectureServiceImpl implements LectureService {

    private final LectureDAO lectureDAO;

    private final UserDAO userDAO;

    @Autowired
    public LectureServiceImpl(LectureDAO lectureDAO, UserDAO userDAO) {
        this.lectureDAO = lectureDAO;
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public List<Lecture> findAll() {
        return lectureDAO.findAll();
    }

    @Override
    @Transactional
    public List<Lecture> findByLecturer(User lecturer) {
        return lectureDAO.findByLecturer(lecturer.getId());
    }

    @Override
    @Transactional
    public Lecture findById(int id) {
        return lectureDAO.findById(id);
    }

    @Override
    @Transactional
    public List<User> findLecturers() {
        return userDAO.findByRole(RoleName.ROLE_LECTURER);
    }

    @Override
    @Transactional
    public void create(Lecture lecture) {
        lectureDAO.create(lecture);
    }

    @Override
    @Transactional
    public void update(Lecture lecture) {
        lectureDAO.update(lecture);
    }

    @Override
    @Transactional
    public void delete(Lecture lecture) {
        lectureDAO.delete(lecture);
    }

    @Override
    @Transactional
    public List<User> findStudents(Lecture lecture) {
        return lectureDAO.findStudents(lecture.getId());
    }

    @Override
    @Transactional
    public User findLecturer(int userId) {
        return userDAO.findById(userId, RoleName.ROLE_LECTURER);
    }

    @Override
    @Transactional
    public User findLecturer(String name) {
        return userDAO.findByUsername(name, RoleName.ROLE_LECTURER);
    }
}