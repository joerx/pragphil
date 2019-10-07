package io.yodo.pragphil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("MEMBER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/**").hasRole("MEMBER")
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
