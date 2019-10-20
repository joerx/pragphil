package io.yodo.pragphil.api;

import io.yodo.pragphil.core.error.ApiErrorResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiExceptionHandlerFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ApiErrorResolver resolver;

    public ApiExceptionHandlerFilter(ApiErrorResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.debug("Exception handler filter processing chain");
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Error while processing filter chain", e);
            resolver.setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e);
        }
    }
}
