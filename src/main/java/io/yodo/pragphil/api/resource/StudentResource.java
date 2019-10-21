package io.yodo.pragphil.api.resource;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class StudentResource {

    private int id;

    private String username;

    @JsonProperty("attended_lectures")
    private List<LectureResource> attendedLectures;

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

    public List<LectureResource> getAttendedLectures() {
        return attendedLectures;
    }

    public void setAttendedLectures(List<LectureResource> attendedLectures) {
        this.attendedLectures = attendedLectures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentResource that = (StudentResource) o;
        return id == that.id &&
                username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public String toString() {
        return "StudentResource{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
