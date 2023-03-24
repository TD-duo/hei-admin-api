package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.BoundedPageSize;
import school.hei.haapi.model.Course;
import school.hei.haapi.model.PageFromOne;
import school.hei.haapi.repository.CourseRepository;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class CourseService {
  private final CourseRepository repository;

  public List<Course> getCourses(PageFromOne page, BoundedPageSize pageSize){
    Pageable pageable = PageRequest.of(
            page.getValue() - 1, pageSize.getValue());
    return repository.findAll(pageable).getContent();
  }


  public static List<Course> getCourses(List<Course> courses, String code, String name, int credits, String teacherFirstName, String teacherLastName) {
    List<Course> filteredCourses = new ArrayList<Course>();

    for (Course course : courses) {
      // apply filters
      if ((code == null || course.getCode().toLowerCase().contains(code.toLowerCase())) &&
              (name == null || course.getName().toLowerCase().contains(name.toLowerCase())) &&
              (credits == 0 || course.getCredits() == credits) &&
              (teacherFirstName == null || course.getMainTeacher().getFirstName().toLowerCase().contains(teacherFirstName.toLowerCase())) &&
              (teacherLastName == null || course.getMainTeacher().getLastName().toLowerCase().contains(teacherLastName.toLowerCase()))) {
        filteredCourses.add(course);
      }
    }

    return filteredCourses;
  }
}