package io.yodo.pragphil.api.resource;

import io.yodo.pragphil.core.domain.entity.Role;
import io.yodo.pragphil.core.domain.entity.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Simplified view of {@link User} for serialization and API usage
 */
public class UserResource {

    private int id;

    private String username;

    private List<String> roles;

    public static UserResource fromUser(User u) {
        UserResource ur = new UserResource();
        ur.setId(u.getId());
        ur.setUsername(u.getUsername());
        ur.setRoles(mapRoles(u.getRoles()));
        return ur;
    }

    private static List<String> mapRoles(List<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
