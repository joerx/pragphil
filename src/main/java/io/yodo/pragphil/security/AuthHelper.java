package io.yodo.pragphil.security;

import io.yodo.pragphil.entity.User;
import io.yodo.pragphil.error.AccessDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Nullable
    private User fromObject(Object principal) {
        log.debug("Attempting to get user from principal " + principal);

        if (principal instanceof RememberMeAuthenticationToken) {
            RememberMeAuthenticationToken rot = (RememberMeAuthenticationToken) principal;
            AppUserDetails aud = (AppUserDetails) rot.getPrincipal();
            return aud.getUser();
        }
        if (principal instanceof AppUserDetails) {
            AppUserDetails aud = (AppUserDetails) principal;
            return aud.getUser();
        }

        log.warn(principal + " cannot be cast to " + AppUserDetails.class);
        return null;
    }

    @Nullable
    public User getUserForPrincipal(Object principal) {
        return fromObject(principal);
    }

    public User mustGetUserForPrincipal(Object principal) throws AccessDeniedException {
        User user = fromObject(principal);
        if (user == null) {
            log.warn("Illegal access detected");
            throw new AccessDeniedException("Authorization is required to access this resource");
        }
        return user;
    }
}
