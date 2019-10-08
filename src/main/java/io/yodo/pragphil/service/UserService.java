package io.yodo.pragphil.service;

import io.yodo.pragphil.entity.Role;
import io.yodo.pragphil.entity.RoleName;
import io.yodo.pragphil.entity.User;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

import static io.yodo.pragphil.entity.RoleName.*;

@Secured(ROLE_ADMIN)
public interface UserService {

    List<User> findAll();

    List<User> findByRole(String roleName);

    @Secured({ROLE_MEMBER, ROLE_ADMIN})
    User findById(int id);

    @Secured({ROLE_MEMBER, ROLE_ADMIN})
    User findByIdWithLectures(int id);

    void create(User user);

    void update(User user);

    void delete(User user);

    void disableUser(User user);

    void enableUser(User user);

    List<Role> getAllRoles();

    Role findRoleById(int roleId);
}
