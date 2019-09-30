package io.yodo.pragphil.dao;

import io.yodo.pragphil.entity.Role;
import io.yodo.pragphil.entity.User;

import java.util.List;

public interface RolesDAO {

    Role findById(int roleId);

    void create(Role r);

    void update(Role r);

    void delete(Role r);

    List<Role> getAllRoles();
}
