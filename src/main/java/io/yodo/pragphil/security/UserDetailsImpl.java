package io.yodo.pragphil.security;

import io.yodo.pragphil.entity.Role;
import io.yodo.pragphil.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    private final Collection<SimpleGrantedAuthority> authorities;

    UserDetailsImpl(User user) {
        this.user = user;
        this.authorities = new ArrayList<>();
        for (Role r : user.getRoles()) {
            SimpleGrantedAuthority sga = new SimpleGrantedAuthority(r.getName().toString());
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
        return "UserDetailsImpl{" +
                "user=" + user +
                ", authorities=" + authorities +
                '}';
    }
}
