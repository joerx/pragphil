package io.yodo.pragphil.core.service;

import io.yodo.pragphil.core.entity.Lecture;
import io.yodo.pragphil.core.entity.Role;
import io.yodo.pragphil.core.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
public interface UserService {

    List<User> findAll();

    List<User> findByRole(String roleName);

    @PreAuthorize("isAuthenticated()")
    User findById(int id);

    @PreAuthorize("isAuthenticated()")
    User findByUsername(String username);

    void create(User user);

    void update(User user);

    void delete(User user);

    void disableUser(User user);

    void enableUser(User user);

    List<Role> getAllRoles();

    Role findRoleById(int roleId);
}
