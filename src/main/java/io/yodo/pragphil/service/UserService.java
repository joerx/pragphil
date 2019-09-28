package io.yodo.pragphil.service;

import io.yodo.pragphil.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(int id);

    User findByUsername(String username);

    void create(User user);

    void update(User user);

    void delete(User user);

    void disableUser(User user);

    void enableUser(User user);
}
