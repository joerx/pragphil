package io.yodo.pragphil.service;

import io.yodo.pragphil.controller.UserEditor;
import io.yodo.pragphil.dao.LectureDAO;
import io.yodo.pragphil.dao.UserDAO;
import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.RoleName;
import io.yodo.pragphil.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

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
}
