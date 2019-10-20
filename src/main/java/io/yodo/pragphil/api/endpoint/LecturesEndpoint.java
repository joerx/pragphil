package io.yodo.pragphil.api.endpoint;

import io.yodo.pragphil.api.resource.LectureResource;
import io.yodo.pragphil.core.entity.Lecture;
import io.yodo.pragphil.core.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/lectures")
public class LecturesEndpoint {

    private final LectureService lectureService;

    @Autowired
    public LecturesEndpoint(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<LectureResource> listLectures() {
        List<Lecture> lectures = lectureService.findAll();
        return lectures.stream().map(LectureResource::fromLecture).collect(Collectors.toList());
    }

    @RequestMapping(path = "/{lectureId}", method = RequestMethod.GET)
    public LectureResource getLecture(@PathVariable int lectureId) {
        Lecture lecture = lectureService.findById(lectureId);
        return LectureResource.fromLecture(lecture);
    }
}
