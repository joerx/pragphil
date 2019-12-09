package io.yodo.pragphil.core.security;

import io.yodo.pragphil.core.domain.entity.Role;
import io.yodo.pragphil.core.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class DefaultUserDetails implements AppUserDetails {

    private final User user;

    private final Collection<SimpleGrantedAuthority> authorities;

    public DefaultUserDetails(User user) {
        this.user = user;
        this.authorities = new ArrayList<>();
        for (Role r : user.getRoles()) {
            SimpleGrantedAuthority sga = new SimpleGrantedAuthority(r.getName());
            authorities.add(sga);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "DefaultUserDetails{" +
                "user=" + user +
                ", authorities=" + authorities +
                '}';
    }
}
