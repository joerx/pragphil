package io.yodo.pragphil.security;

import io.yodo.pragphil.dao.UserDAO;
import io.yodo.pragphil.entity.User;
import io.yodo.pragphil.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.NoResultException;
import java.util.logging.Logger;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger log = Logger.getLogger(getClass().getName());

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.findByUsername(username);
            UserDetails ud = new UserDetailsImpl(user);
            log.info("Loaded user details " + ud);
            return ud;
        } catch (NoResultException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
