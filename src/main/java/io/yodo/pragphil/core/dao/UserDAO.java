package io.yodo.pragphil.core.dao;

import io.yodo.pragphil.core.entity.User;

import java.util.List;

public interface UserDAO {

    List<User> findAll();

    List<User> findByRole(String rolename);

    User findById(int id);

    User findByUsername(String username);

    void create(User user);

    void update(User user);

    void delete(User user);

    User findByToken(Object token);
}
