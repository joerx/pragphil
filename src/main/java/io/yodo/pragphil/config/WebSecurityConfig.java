package io.yodo.pragphil.config;

import io.yodo.pragphil.dao.UserDAO;
import io.yodo.pragphil.security.UserDetailsServiceImpl;
import io.yodo.pragphil.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger log = Logger.getLogger(getClass().getName());

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Autowired
    public UserDetailsService userDetailsService(UserDAO userDAO) {
        return new UserDetailsServiceImpl(userDAO);
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
                    .permitAll()
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access-denied");
    }
}
