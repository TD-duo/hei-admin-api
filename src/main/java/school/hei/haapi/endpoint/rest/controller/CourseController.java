package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.haapi.endpoint.rest.mapper.CourseMapper;
import school.hei.haapi.model.Course;
import org.springframework.web.bind.annotation.*;
import school.hei.haapi.endpoint.rest.mapper.CourseMapper;
import school.hei.haapi.endpoint.rest.model.Course;
import school.hei.haapi.endpoint.rest.model.CourseStatus;
import school.hei.haapi.endpoint.rest.model.CrupdateCourse;
import school.hei.haapi.endpoint.rest.model.UpdateStudentCourse;
import school.hei.haapi.model.BoundedPageSize;
import school.hei.haapi.model.PageFromOne;
import school.hei.haapi.service.CourseService;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toUnmodifiableList;

@RestController
@AllArgsConstructor
public class CourseController {
    private final CourseService service;
    private final CourseMapper mapper;

    @GetMapping(value = "/courses")
    public List<Course> getCourses(@RequestParam(name = "code",required = false, defaultValue = "ASC") String code,
                                   @RequestParam(name = "name") String name,
                                   @RequestParam(name = "credits",required = false, defaultValue = "ASC") Integer credits,
                                   @RequestParam(name = "teacher_first_name") String teacherFirstName,
                                   @RequestParam(name = "teacher_last_name") String teacherLastName) {
        Sort sortCredits = credits.equals("DESC") ? Sort.by("credits").descending() : Sort.by("credits").ascending();
        Sort sortCode = code.equals("DESC") ? Sort.by("code").descending() : Sort.by("code").ascending();
        List<school.hei.haapi.model.Course> filter = service.getCourses(code, name, credits, teacherFirstName, teacherLastName);
        return filter.stream()
                .map(mapper::toRest)
                .collect(Collectors.toList());
  }
}
