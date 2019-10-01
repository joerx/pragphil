package io.yodo.pragphil.service;

import io.yodo.pragphil.dao.RolesDAO;
import io.yodo.pragphil.dao.UserDAO;
import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.Role;
import io.yodo.pragphil.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = Logger.getLogger(getClass().getName());

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
    public User findByIdWithLectures(int id) {
        return userDAO.findByIdWithLectures(id);
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    @Transactional
    public void create(User dto) {
        userDAO.create(dto);
    }

    @Override
    @Transactional
    public void update(User user) {
        // We need to preserve the password if it wasn't supposed to be updated and we can't tell Hibernate to just
        // ignore a null field. (Why? Who designs crap like this?)
        if (user.getPassword() == null) {
            String pw = userDAO.getPassword(user.getId());
            user.setPassword(pw);
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
    @Transactional
    public List<Role> getAllRoles() {
        return rolesDAO.getAllRoles();
    }

    @Override
    @Transactional
    public Role findRoleById(int roleId) {
        return rolesDAO.findById(roleId);
    }
}
