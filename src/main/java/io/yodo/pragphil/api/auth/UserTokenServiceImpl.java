package io.yodo.pragphil.api.auth;

import io.yodo.pragphil.dao.UserDAO;
import io.yodo.pragphil.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserTokenServiceImpl implements UserTokenService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserDAO userDAO;

    @Autowired
    public UserTokenServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public User findByToken(Object token) {
        log.debug("Trying to retrieve API user for token " + token);
        return userDAO.findByToken(token);
    }
}
