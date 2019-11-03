package io.yodo.pragphil.api.endpoint;

import io.yodo.pragphil.api.resource.LectureResource;
import io.yodo.pragphil.api.resource.mapper.LectureMapper;
import io.yodo.pragphil.core.domain.entity.Lecture;
import io.yodo.pragphil.core.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LecturesEndpoint {

    private final LectureService lectureService;

    private final LectureMapper lectureMapper;

    @Autowired
    public LecturesEndpoint(LectureService lectureService, LectureMapper lectureMapper) {
        this.lectureService = lectureService;
        this.lectureMapper = lectureMapper;
    }

    @RequestMapping(path = "/lectures", method = RequestMethod.GET)
    public List<LectureResource> listLectures() {
        List<Lecture> lectures = lectureService.findAll();

        return lectures.stream().map(lectureMapper::toResource).collect(Collectors.toList());
    }

    @RequestMapping(path = "/lectures/{lectureId}", method = RequestMethod.GET)
    public LectureResource getLecture(@PathVariable int lectureId) {
        Lecture lecture = lectureService.findById(lectureId);

        return lectureMapper.toResource(lecture);
    }

    @RequestMapping(path = "/lectures", method = RequestMethod.POST)
    public LectureResource createLecture(@RequestBody LectureResource lectureResource) {
        Lecture lecture = lectureMapper.toLecture(lectureResource);

        lectureService.create(lecture);

        return lectureMapper.toResource(lecture);
    }

    @RequestMapping(path = "/lectures/{id}", method = RequestMethod.PUT)
    public LectureResource updateLecture(@PathVariable int id, @RequestBody LectureResource lectureResource) {
        Lecture lecture = lectureMapper.toLecture(lectureResource, lectureService.findById(id));

        lectureService.update(lecture);

        return lectureMapper.toResource(lecture);
    }

    @RequestMapping(path = "/lectures/{id}", method = RequestMethod.DELETE)
    public LectureResource deleteLecture(@PathVariable int id) {
        Lecture lecture = lectureService.findById(id);

        lectureService.delete(lecture);

        return lectureMapper.toResource(lecture);
    }
}
