package io.yodo.pragphil.entity;

import io.yodo.pragphil.validation.Password;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Entity
@Table(name = "users")
@Password(message = User.PASSWORD_VALIDATION_MSG, minLength = User.PASSWORD_MIN_LENGTH)
// Serializable: see https://hibernate.atlassian.net/browse/HHH-7668
public class User implements Serializable  {

    @Transient
    private final Logger log = Logger.getLogger(getClass().getName());

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

    @OneToMany(fetch=FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "roles_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "lecturer")
    private List<Lecture> conductedLectures;

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
        roles.remove(r);
    }

    public void addRole(Role r) {
        roles.add(r);
    }

    public List<Lecture> getConductedLectures() {
        return conductedLectures;
    }

    public void setConductedLectures(List<Lecture> conductedLectures) {
        this.conductedLectures = conductedLectures;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        log.info("User " + o + " equals " + this + "?");
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
