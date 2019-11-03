package io.yodo.pragphil.api.resource.mapper;

import io.yodo.pragphil.api.resource.LectureResource;
import io.yodo.pragphil.core.domain.entity.Lecture;
import io.yodo.pragphil.core.domain.entity.User;
import io.yodo.pragphil.core.error.InvalidArgumentException;
import io.yodo.pragphil.core.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;

@Component
public class LectureMapper {

    private final LectureService lectureService;

    @Autowired
    public LectureMapper(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    private User findLecturer(String username) {
        try {
            return lectureService.findLecturer(username);
        } catch (NoResultException ex) {
            throw new InvalidArgumentException("Invalid value for lecturer");
        }
    }

    public Lecture toLecture(LectureResource lectureResource) {
        return toLecture(lectureResource, new Lecture());
    }

    public Lecture toLecture(LectureResource lectureResource, Lecture lecture) {
        lecture.setLecturer(findLecturer(lectureResource.getLecturer()));
        lecture.setName(lectureResource.getName());
        return lecture;
    }

    public LectureResource toResource(Lecture lecture) {
        LectureResource lr = new LectureResource();
        lr.setId(lecture.getId());
        if (lecture.getLecturer() != null) {
            lr.setLecturer(lecture.getLecturer().getUsername());
        }
        lr.setName(lecture.getName());
        return lr;
    }
}
