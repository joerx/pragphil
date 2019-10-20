package io.yodo.pragphil.core.service;

import io.yodo.pragphil.core.dao.LectureDAO;
import io.yodo.pragphil.core.dao.RolesDAO;
import io.yodo.pragphil.core.dao.UserDAO;
import io.yodo.pragphil.core.entity.Lecture;
import io.yodo.pragphil.core.entity.Role;
import io.yodo.pragphil.core.entity.User;
import io.yodo.pragphil.core.error.InvalidRequestException;
import io.yodo.pragphil.core.error.NoSuchThingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final RolesDAO rolesDAO;

    private final LectureDAO lectureDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RolesDAO rolesDAO, LectureDAO lectureDAO) {
        this.userDAO = userDAO;
        this.rolesDAO = rolesDAO;
        this.lectureDAO = lectureDAO;
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
        userDAO.create(dto);
    }

    @Override
    @Transactional
    public void update(User form) {
        User u1 = findById(form.getId());

        if (u1 == null) {
            throw new NoSuchThingException("No user with id " + form.getId());
        }
        if (!form.isLecturer() && u1.getConductedLectures().size() > 0) {
            throw new InvalidRequestException("User is still conducting lectures, please reassign these first");
        }
        if (!form.isStudent() && u1.getAttendedLectures().size() > 0) {
            throw new InvalidRequestException("User is still enrolled in lectures, please delist her first");
        }

        // remap to ensure all non-updated fields and associations are preserved
        u1.setRoles(form.getRoles());
        u1.setId(form.getId());
        u1.setUsername(form.getUsername());
        u1.setEnabled(form.isEnabled());

        // update password only if it was set before
        if (form.getPassword() != null && form.getPassword().length() > 0) {
            u1.setPassword(form.getPassword());
        }

        userDAO.update(u1);
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
