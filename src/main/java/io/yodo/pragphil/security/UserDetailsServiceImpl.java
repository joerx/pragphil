package io.yodo.pragphil.security;

import io.yodo.pragphil.dao.UserDAO;
import io.yodo.pragphil.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.logging.Logger;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger log = Logger.getLogger(getClass().getName());

    private final UserDAO userDAO;

    public UserDetailsServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userDAO.findByUsername(username);
            UserDetails ud = new AppUserDetailsImpl(user);
            log.info("Loaded user details " + ud);
            return ud;
        } catch (NoResultException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
