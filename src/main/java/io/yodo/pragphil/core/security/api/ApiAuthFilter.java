package io.yodo.pragphil.core.security.api;

import io.yodo.pragphil.core.error.ApiErrorResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This filter implements authentication based on API tokens. To keep things simple we're using a plain token
 * stored in the database along with the user record. A {@link UserTokenService} is in charge of retrieving the
 * token from the database, it's used by the custom auth provider implemented in {@link ApiAuthProvider}
 *
 * The filter chain is global, sitting in front of the servlet configurations. To signify which request should
 * be handled by this filter, it expects a {@link RequestMatcher} that should only match requests to the API.
 * See {@link io.yodo.pragphil.config.ApiSecurityConfig} for a usage example.
 */
public class ApiAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ApiErrorResolver errorResolver;

    public ApiAuthFilter(final RequestMatcher requiresAuth, ApiErrorResolver errorResolver) {
        super(requiresAuth);
        this.errorResolver = errorResolver;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
        log.debug("Attempting authentication");

        String tokenHeader = httpServletRequest.getHeader("Authorization");

        if (tokenHeader == null) {
            throw new InvalidAuthenticationException("No token found in request");
        }
        if (!tokenHeader.matches("^Token\\s\\w+")) {
            throw new InvalidAuthenticationException("Invalid format for auth header");
        }

        String token = tokenHeader.replaceFirst("Token\\s+", "").trim();
        log.debug("Found a token, will try to authenticate");

        Authentication auth = new ApiAuthenticationToken(token);
        log.debug("Got auth " + auth);

        return getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.debug("Authentication was successful, setting security context auth to " + authResult);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.debug("Authentication failed: " + failed.getMessage());
        SecurityContextHolder.clearContext();
        errorResolver.setErrorResponse(HttpStatus.UNAUTHORIZED, response, failed);
    }

    static class InvalidAuthenticationException extends AuthenticationException {
        InvalidAuthenticationException(String msg) {
            super(msg);
        }
    }
}
