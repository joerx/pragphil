package io.yodo.pragphil.core.domain.dao;

import io.yodo.pragphil.core.domain.entity.Role;

import java.util.List;

public interface RolesDAO {

    Role findById(int roleId);

    void create(Role r);

    void update(Role r);

    void delete(Role r);

    List<Role> getAllRoles();
}
