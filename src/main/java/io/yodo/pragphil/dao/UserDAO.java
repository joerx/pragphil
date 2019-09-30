package io.yodo.pragphil.dao;

import io.yodo.pragphil.entity.User;

import java.util.List;

public interface UserDAO {

    List<User> findAll();

    List<User> findByRole(String rolename);

    User findById(int id);

    User findByUsername(String username);

    void create(User user);

    void update(User user);

    void delete(User user);
}