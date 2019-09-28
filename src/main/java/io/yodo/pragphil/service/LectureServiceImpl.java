package io.yodo.pragphil.service;

import io.yodo.pragphil.dao.LectureDAO;
import io.yodo.pragphil.entity.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LectureServiceImpl implements LectureService {

    private final LectureDAO lectureDAO;

    @Autowired
    public LectureServiceImpl(LectureDAO lectureDAO) {
        this.lectureDAO = lectureDAO;
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
