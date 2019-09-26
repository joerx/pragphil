package io.yodo.pragphil.dao;

import io.yodo.pragphil.entity.Lecture;

import java.util.List;

public interface LecturesDAO {

    List<Lecture> findAll();

    Lecture findById(int id);

    void create(Lecture l);

    void update(Lecture l);

    void delete(Lecture l);
}
