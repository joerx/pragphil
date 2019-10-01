package io.yodo.pragphil.service;

import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.User;

import java.util.List;

public interface LectureService {

    List<Lecture> findAll();

    Lecture findById(int id);

    List<User> findLecturers();

    void create(Lecture lecture);

    void update(Lecture lecture);

    void delete(Lecture lecture);
}
