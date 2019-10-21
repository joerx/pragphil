package io.yodo.pragphil.api.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_ABSENT)
public class EnrollmentResource {

    @JsonProperty("lecture_id")
    private int lectureId;

    @JsonProperty("lecture_name")
    private String lectureName;

    @JsonProperty("student_name")
    private String studentName;

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentResource that = (EnrollmentResource) o;
        return lectureId == that.lectureId &&
                studentName.equals(that.studentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lectureId, studentName);
    }

    @Override
    public String toString() {
        return "EnrollmentResource{" +
                "lectureId=" + lectureId +
                ", studentName=" + studentName +
                '}';
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getLectureName() {
        return lectureName;
    }
}
