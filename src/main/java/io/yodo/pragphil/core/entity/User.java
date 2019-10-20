package io.yodo.pragphil.core.entity;

import io.yodo.pragphil.core.validation.Password;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@SuppressWarnings("unused")
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
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

    @OneToMany(fetch= EAGER, cascade = {MERGE, PERSIST})
    @JoinTable(
            name = "roles_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "lecturer")
    private List<Lecture> conductedLectures;

    @ManyToMany(cascade = {MERGE, REFRESH}, fetch = LAZY)
    @JoinTable(
            name = "lectures_students",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "lecture_id")
    )
    private List<Lecture> attendedLectures;

    @Column(name = "api_token")
    private String apiToken;

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

    public List<Lecture> getAttendedLectures() {
        return attendedLectures;
    }

    public void setAttendedLectures(List<Lecture> attendedLectures) {
        this.attendedLectures = attendedLectures;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public void enroll(Lecture l) {
        attendedLectures.add(l);
        l.getStudents().add(this);
    }

    public void delist(Lecture l) {
        attendedLectures.remove(l);
        l.getStudents().remove(this);
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isStudent() {
        return hasRole(RoleName.ROLE_STUDENT);
    }

    public boolean isLecturer() {
        return hasRole(RoleName.ROLE_LECTURER);
    }

    public boolean isAdmin() {
        return hasRole(RoleName.ROLE_ADMIN);
    }

    public boolean hasRole(String role) {
        return roles.contains(Role.byName(role));
    }

    public boolean isEnrolledIn(Lecture l) {
        return attendedLectures.contains(l);
    }


}
