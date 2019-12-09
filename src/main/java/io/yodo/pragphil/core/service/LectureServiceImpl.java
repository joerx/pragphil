package io.yodo.pragphil.core.service;

import io.yodo.pragphil.core.domain.dao.LectureDAO;
import io.yodo.pragphil.core.domain.dao.UserDAO;
import io.yodo.pragphil.core.domain.entity.Lecture;
import io.yodo.pragphil.core.domain.entity.RoleName;
import io.yodo.pragphil.core.domain.entity.User;
import io.yodo.pragphil.core.domain.paging.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LectureServiceImpl implements LectureService {

    private final LectureDAO lectureDAO;

    private final UserDAO userDAO;

    private final int recordsPerPage;

    @Autowired
    public LectureServiceImpl(
            LectureDAO lectureDAO,
            UserDAO userDAO,
            @Value("${paging.recordsPerPage}") int recordsPerPage
    ) {
        this.lectureDAO = lectureDAO;
        this.userDAO = userDAO;
        this.recordsPerPage = recordsPerPage;
    }

    @Override
    @Transactional
    public List<Lecture> findAll() {
        return lectureDAO.findAll();
    }

    @Override
    @Transactional
    public Page<Lecture> findOnPage(int pageNo) {
        return lectureDAO.findOnPage(pageNo, recordsPerPage);
    }

    @Override
    @Transactional
    public Page<Lecture> findByLecturer(User lecturer, int pageNo) {
        return lectureDAO.findByLecturer(lecturer.getId(), pageNo, recordsPerPage);
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
