package io.yodo.pragphil.service;

import io.yodo.pragphil.dao.RolesDAO;
import io.yodo.pragphil.dao.UserDAO;
import io.yodo.pragphil.entity.Role;
import io.yodo.pragphil.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final RolesDAO rolesDAO;

    private static final String[] ALL_ROLE_NAMES = new String[]{
            "ROLE_MEMBER",
            "ROLE_LECTURER",
            "ROLE_STUDENT"
    };

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RolesDAO rolesDAO) {
        this.userDAO = userDAO;
        this.rolesDAO = rolesDAO;
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    @Transactional
    public List<User> findByRole(String rolename) {
        return userDAO.findByRole(rolename);
    }

    @Override
    @Transactional
    public User findById(int id) {
        return userDAO.findById(id);
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    @Transactional
    public void create(User dto) {
        User user = new User();

        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());
        user.setEnabled(dto.isEnabled());

        for (Role r : dto.getRoles()) {
            user.addRole(r);
        }

        userDAO.create(user);
        dto.setId(user.getId());
    }

    @Override
    @Transactional
    public void update(User dto) {
        // Automated form bindings and ORMs only work in a very narrow frame of acceptable use cases,
        // otherwise they are a headache-inducing cartload of horseshit. so we need to map this by hand...
        User user = new User();

        user.setId(dto.getId());
        user.setPassword(dto.getPassword());
        user.setUsername(dto.getUsername());
        user.setEnabled(dto.isEnabled());

        rolesDAO.deleteForUser(user);
        for (Role r : dto.getRoles()) {
            user.addRole(r);
            rolesDAO.create(r);
        }

        userDAO.update(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Override
    @Transactional
    public void disableUser(User user) {
        if (!user.isEnabled()) {
            throw new IllegalStateException("User " + user.getId() + " is already disabled");
        }
        user.setEnabled(false);
        userDAO.update(user);
    }

    @Override
    @Transactional
    public void enableUser(User user) {
        if (user.isEnabled()) {
            throw new IllegalStateException("User " + user.getId() + " is already enabled");
        }
        user.setEnabled(true);
        userDAO.update(user);
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>(ALL_ROLE_NAMES.length);
        for (String name : ALL_ROLE_NAMES) {
            Role r = new Role();
            r.setRole(name);
            roles.add(r);
        }
        return roles;
    }

    @Override
    @Transactional
    public Role getRoleById(int roleId) {
        return rolesDAO.getById(roleId);
    }
}
