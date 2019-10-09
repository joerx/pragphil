package io.yodo.pragphil.security;

import io.yodo.pragphil.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.logging.Logger;

public class PrincipalHelper {

    private static final Logger log = Logger.getLogger(PrincipalHelper.class.getName());

    private static User fromObject(Object principal) {
        if (!(principal instanceof PragPhilUserDetails)) {
            log.warning(principal + " cannot be cast to " + PragPhilUserDetails.class);
            return new User();
        }
        return ((PragPhilUserDetails) principal).getUser();
    }

    public static User getUser(UserDetails principal) {
        return fromObject(principal);
    }

    public static User getUser(Authentication auth) {
        return fromObject(auth.getPrincipal());
    }
}
