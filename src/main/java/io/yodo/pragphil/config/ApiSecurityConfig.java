package io.yodo.pragphil.config;

import io.yodo.pragphil.core.error.ApiErrorResolver;
import io.yodo.pragphil.api.ApiExceptionHandlerFilter;
import io.yodo.pragphil.core.security.api.ApiAuthFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@Order(1)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/**")
    );

    private final AuthenticationProvider authProvider;

    private final ApiErrorResolver errorResolver;

    public ApiSecurityConfig(AuthenticationProvider authProvider, ApiErrorResolver errorResolver) {
        this.authProvider = authProvider;
        this.errorResolver = errorResolver;
    }

    @Bean
    public ApiAuthFilter authenticationFilter() throws Exception {
        final ApiAuthFilter filter = new ApiAuthFilter(PROTECTED_URLS, errorResolver);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    public ApiExceptionHandlerFilter exceptionHandlerFilter() {
        return new ApiExceptionHandlerFilter(errorResolver);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("Configuring http security");

        http.antMatcher("/api/**")
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .exceptionHandling()
            .and()
                .authenticationProvider(authProvider)
                .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter(), ApiAuthFilter.class)
                .authorizeRequests()
            .requestMatchers(PROTECTED_URLS)
                .authenticated()
            .and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .logout().disable();
    }
}
