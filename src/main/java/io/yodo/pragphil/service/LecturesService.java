package io.yodo.pragphil.service;

import io.yodo.pragphil.entity.Lecture;

import java.util.List;

public interface LecturesService {

    List<Lecture> findAll();

    Lecture findById(int id);
}
