package io.yodo.pragphil.security;

import io.yodo.pragphil.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.logging.Logger;

@Component
public class AuthHelper {

    private static final Logger log = Logger.getLogger(AuthHelper.class.getName());

    private static User fromObject(Object principal) {
        if (!(principal instanceof AppUserDetails)) {
            log.warning(principal + " cannot be cast to " + AppUserDetails.class);
            throw new IllegalStateException(
                    "No current user or invalid principal. Cannot cast " + principal + " to " + AppUserDetails.class);
        }
        return ((AppUserDetails) principal).getUser();
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return fromObject(auth.getPrincipal());
    }

    public User getUserForPrincipal(Object principal) {
        return fromObject(principal);
    }
}
