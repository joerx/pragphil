package io.yodo.pragphil.api.auth;

import io.yodo.pragphil.entity.User;
import io.yodo.pragphil.security.DefaultUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;

@Component
public class ApiAuthProvider implements AuthenticationProvider {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserTokenService userTokenService;

    @Autowired
    public ApiAuthProvider(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug(authentication + " is to be authenticated");

        Object token = authentication.getCredentials();
        UserDetails userDetails = retrieveUser(token);

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                authentication.getCredentials(),
                userDetails.getAuthorities()
            );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(ApiAuthenticationToken.class);
    }

    private UserDetails retrieveUser(Object token) throws AuthenticationException {
        try {
            User user = userTokenService.findByToken(token);
            log.debug("Found user " + user);

            return new DefaultUserDetails(user);
        } catch (NoResultException ex) {
            log.warn("No user found for provided API token");
            throw new UnknownTokenException("No user found for provided API token");
        }
    }

    static class UnknownTokenException extends AuthenticationException {
        UnknownTokenException(String msg) {
            super(msg);
        }
    }
}
