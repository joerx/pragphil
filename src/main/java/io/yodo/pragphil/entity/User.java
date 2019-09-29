package io.yodo.pragphil.entity;

import io.yodo.pragphil.validation.Password;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * FIXME: use of password validation & encryption is ambiguous and overloaded
 * Users need to be treated somewhat special with regard to the password being set on a new user vs. an existing user,
 * due to the mapping between encrypted and unencrypted state of passwords.
 * The constraints for a valid user should simply be: must have an encrypted password and the password property is
 * encrypted at all times.
 * Instead, currently the plain text password is bound to the User object in the view and replaced with the encrypted
 * password on the service layer. This can make reasoning over the current state of the password at different points in
 * the flow confusing as logic gets more complex later (e.g. new ways of registering users being introduced)
 * A cleaner approach might be to separate data binding from persistence by introducing a UserDTO, to bind view
 * properties, including the plain-text password to, perform password validation on - Then map its properties,
 * including the encrypted password to the DAO for persistence.
 */
@Entity
@Table(name = "users")
@Password(message = User.PASSWORD_VALIDATION_MSG, minLength = User.PASSWORD_MIN_LENGTH)
// Serializable: see https://hibernate.atlassian.net/browse/HHH-7668
public class User implements Serializable  {

    private static final String USERNAME_VALIDATION_MSG =
            "must be between 3-50 characters long and contain only characters, numbers and underscores";

    static final int PASSWORD_MIN_LENGTH = 8;

    static final String PASSWORD_VALIDATION_MSG =
            "must be at least " + PASSWORD_MIN_LENGTH + " characters long";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    @NotNull(message = "is required")
    @Pattern(regexp = "\\w{3,50}", message = USERNAME_VALIDATION_MSG)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToMany(mappedBy = "user", fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Role> roles = new ArrayList<>();

    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void removeRole(Role r) {
        if (r == null) return;
        r.setUser(null);
        roles.remove(r);
    }

    public void addRole(Role r) {
        if (r == null) return;
        r.setUser(this);
        roles.add(r);
    }

    public boolean hasRole(Role role) {
        for (Role r : roles) {
            if (r.getRole().equals(role.getRole())) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                enabled == user.enabled &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, enabled);
    }
}
