package io.yodo.pragphil.dao;

import io.yodo.pragphil.entity.Role;
import io.yodo.pragphil.entity.User;

public interface RolesDAO {

    Role getById(int roleId);

    void create(Role r);

    void update(Role r);

    void delete(Role r);

    void deleteForUser(User user);
}
