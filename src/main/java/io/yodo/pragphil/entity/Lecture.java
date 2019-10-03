package io.yodo.pragphil.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
@Entity
@Table(name = "lectures")
public class Lecture {

    private static final int LECTURE_NAME_MIN_LENGTH = 5;

    private static final String LECTURE_NAME_VALIDATION_MSG =
            "should be longer than " + LECTURE_NAME_MIN_LENGTH + " characters";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotNull(message = "is required")
    @Size(min = LECTURE_NAME_MIN_LENGTH, message = LECTURE_NAME_VALIDATION_MSG)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lecturer_id", referencedColumnName = "id")
    private User lecturer;

    @ManyToMany(mappedBy = "attendedLectures")
    private List<User> students;

    public Lecture() {
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

    public User getLecturer() {
        return lecturer;
    }

    public void setLecturer(User lecturer) {
        this.lecturer = lecturer;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public List<User> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return id == lecture.id &&
                Objects.equals(name, lecture.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
