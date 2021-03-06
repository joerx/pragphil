package io.yodo.pragphil.web.controller;

import io.yodo.pragphil.core.security.AppUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


/**
 * Helper class to inject the current principals {@link AppUserDetails} into each view if a user is logged in.
 * Gracefully handles cases where {@link SecurityContextHolder#getContext().getAuthentication()} is null or
 * not an instance of {@link AppUserDetails}
 *
 * Views still need to account for the case that no <code>userDetails</code> attribute is set, e.g. by
 * using <code>empty</code> checks.
 */
@ControllerAdvice
public class UserDetailsAdvice {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ModelAttribute
    public void userDetailsModel(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Current securityContext.authentication is " + auth);
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("auth", auth);
        }
    }
}
