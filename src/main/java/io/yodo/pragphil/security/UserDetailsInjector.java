package io.yodo.pragphil.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.logging.Logger;

/**
 * Helper class to inject the current principals {@link UserDetailsImpl} into each view if a user is logged in.
 * Gracefully handles cases where {@link SecurityContextHolder#getContext().getAuthentication()} is null or
 * not an instance of {@link UserDetailsImpl}
 *
 * Views still need to account for the case that no <code>userDetails</code> attribute is set, e.g. by
 * using <code>empty</code> checks.
 */
@ControllerAdvice
public class UserDetailsInjector {

    private final Logger log = Logger.getLogger(getClass().getName());

    @ModelAttribute
    public void injectUserDetails(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            log.info("Not authenticated");
            return;
        }

        Object principal = auth.getPrincipal();
        log.info("Found principal " + principal);

        if (principal instanceof UserDetailsImpl) {
            model.addAttribute("userDetails", principal);
            return;
        }

        log.warning("Cannot cast " + principal + " to " + UserDetailsImpl.class);
    }
}
