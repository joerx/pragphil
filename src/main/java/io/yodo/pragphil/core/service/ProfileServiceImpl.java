package io.yodo.pragphil.core.service;

import io.yodo.pragphil.core.domain.dao.UserDAO;
import io.yodo.pragphil.core.domain.entity.User;
import io.yodo.pragphil.core.domain.entity.UserProfile;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserDAO userDAO;

    public ProfileServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserProfile findPublicProfile(String username) {
        User user = userDAO.findByUsername(username);
        return new UserProfile(user);
    }

    @Override
    public UserProfile.Private findPrivateProfile(String username) {
        User user = userDAO.findByUsername(username);
        return new UserProfile.Private(user);
    }
}
