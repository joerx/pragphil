package io.yodo.pragphil.dao;

import io.yodo.pragphil.entity.RoleName;
import io.yodo.pragphil.entity.User;

import java.util.List;

public interface UserDAO {

    List<User> findAll();

    List<User> findByRole(RoleName rolename);

    User findById(int id);

    User findByUsername(String username);

    String getPassword(int userId);

    void create(User user);

    void update(User user);

    void delete(User user);

    User findByIdWithLectures(int id);
}
