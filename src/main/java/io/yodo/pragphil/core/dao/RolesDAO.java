package io.yodo.pragphil.core.dao;

import io.yodo.pragphil.core.entity.Role;

import java.util.List;

public interface RolesDAO {

    Role findById(int roleId);

    void create(Role r);

    void update(Role r);

    void delete(Role r);

    List<Role> getAllRoles();
}
