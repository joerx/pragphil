package io.yodo.pragphil.config;

import io.yodo.pragphil.core.domain.dao.UserDAO;
import io.yodo.pragphil.core.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDAO userDAO;

    public WebSecurityConfig(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userDAO);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // We have a custom auth provider for api tokens which only handles ApiAuthenticationToken
        // This will explicitly register a DaoAuthenticationProvider to handle UsernamePasswordAuthenticationToken
        auth.userDetailsService(userDetailsService());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // We're using MethodSecurity on the service layer for access control already. To avoid conflicts, we keep
        // things lightweight here and only require some kind of authentication for backend access.
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticate")
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/?logout")
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access-denied")
                .and()
                    .rememberMe().key("pragphilRememberMeSecret")
                    .userDetailsService(userDetailsService());
    }
}
