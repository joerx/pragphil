package io.yodo.pragphil.api.resource;

import io.yodo.pragphil.core.entity.Lecture;

import java.util.Objects;

/**
 * Simplified view of {@link Lecture} for serialization and API usage
 */
public class LectureResource {

    private int id;

    private String name;

    private String lecturer;

    public static LectureResource fromLecture(Lecture lecture) {
        LectureResource lr = new LectureResource();
        lr.id = lecture.getId();
        lr.lecturer = lecture.getLecturer().getUsername();
        lr.name = lecture.getName();
        return lr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LectureResource that = (LectureResource) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "LectureResource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lecturer='" + lecturer + '\'' +
                '}';
    }
}
