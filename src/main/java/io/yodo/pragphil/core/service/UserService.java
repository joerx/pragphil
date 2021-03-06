package io.yodo.pragphil.core.service;

import io.yodo.pragphil.core.domain.entity.Role;
import io.yodo.pragphil.core.domain.entity.User;
import io.yodo.pragphil.core.domain.paging.Page;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserService {

    @PreAuthorize("hasRole('ADMIN')")
    List<User> findAll();

    @PreAuthorize("hasRole('ADMIN')")
    Page<User> findOnPage(int pageNo);

    @PreAuthorize("hasRole('ADMIN')")
    Page<User> findByRole(String roleName, int pageNo);

    @PostAuthorize("hasRole('ADMIN') or (isAuthenticated() and principal.username == returnObject.username)")
    User findById(int id);

    @PreAuthorize("hasRole('ADMIN') or (isAuthenticated() and principal.username == #username)")
    User findByUsername(String username);

    void create(User user);

    @PreAuthorize("hasRole('ADMIN') or (isAuthenticated() and principal.username == #user.username)")
    void update(User user);

    @PreAuthorize("hasRole('ADMIN') or (isAuthenticated() and principal.username == #user.username)")
    void delete(User user);

    void disableUser(User user);

    void enableUser(User user);

    @PreAuthorize("isAuthenticated()")
    List<Role> getAllRoles();

    @PreAuthorize("isAuthenticated()")
    Role findRoleById(int roleId);
}
