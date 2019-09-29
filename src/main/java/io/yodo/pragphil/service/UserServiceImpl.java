package io.yodo.pragphil.service;

import io.yodo.pragphil.dao.UserDAO;
import io.yodo.pragphil.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userDAO.findAll();
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
    public void create(User user) {
        user.setPassword(encodePassword(user.getPassword()));
        userDAO.create(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        if (user.getPassword() != null && user.getPassword().length() > 0) {
            // password change, encode new password
            user.setPassword(encodePassword(user.getPassword()));
            userDAO.update(user);
        } else {
            // no password change, reset password to original value
            User u2 = userDAO.findById(user.getId());
            // we cannot write back the new user object since the Hibernate session is already associated with
            // the one we just retrieved from the DB
            // instead we map the props for the new user back to the one from the DB
            u2.setUsername(user.getUsername());
            u2.setEnabled(user.isEnabled());
            userDAO.update(u2);
        }
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

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
