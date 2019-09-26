package io.yodo.pragphil.service;

import io.yodo.pragphil.dao.LecturesDAO;
import io.yodo.pragphil.entity.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LecturesServiceImpl implements LecturesService {

    private final LecturesDAO lecturesDAO;

    @Autowired
    public LecturesServiceImpl(LecturesDAO lecturesDAO) {
        this.lecturesDAO = lecturesDAO;
    }

    @Override
    @Transactional
    public List<Lecture> findAll() {
        return lecturesDAO.findAll();
    }

    @Override
    @Transactional
    public Lecture findById(int id) {
        return lecturesDAO.findById(id);
    }
}
