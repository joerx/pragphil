package io.yodo.pragphil.core.domain.dao;

import io.yodo.pragphil.core.domain.entity.User;
import io.yodo.pragphil.core.domain.paging.Page;

import java.util.List;

public interface UserDAO {

    List<User> findAll();

    Page<User> findOnPage(int pageNo, int numRecords);

    Page<User> findByRole(String rolename, int pageNo, int numRecords);

    List<User> findByRole(String role);

    User findById(int id);

    User findById(int id, String role);

    User findByUsername(String username);

    User findByUsername(String username, String role);

    void create(User user);

    void update(User user);

    void delete(User user);

    User findByToken(Object token);
}
