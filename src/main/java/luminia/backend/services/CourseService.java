package luminia.backend.services;

import lombok.AllArgsConstructor;
import luminia.backend.dto.CourseAccessDto;
import luminia.backend.dto.CourseDto;
import luminia.backend.models.Course;
import luminia.backend.models.CourseAccess;
import luminia.backend.models.User;
import luminia.backend.repositories.CourseAccessRepository;
import luminia.backend.repositories.CourseRepository;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService {
    private UserService userService;
    private CourseRepository courseRepository;
    private CourseAccessRepository courseAccessRepository;

    @Transactional
    public Optional<Course> findWithFetchAll(Long courseId) {
        return courseRepository.findWithFetchAll(courseId);
    }

    @Transactional
    public Page<CourseAccess> findByUser(User user, Pageable pageable) {
        return courseAccessRepository.findByUserFetchCourse(user, pageable);
    }

    @Transactional
    public Optional<CourseAccess> findAccessByCourseAndUser(Course course, User user) {
        return courseAccessRepository.findByCourseAndUser(course, user);
    }

    @Transactional
    public Page<CourseAccess> findByUserWithSort(User user, int pageId, int pageSize) {
        return findByUser(user, PageRequest.of(pageId, pageSize, Sort.by("status")));
    }

    @Transactional
    public Optional<Course> findById(Long aLong) {
        return courseRepository.findById(aLong);
    }

    @Transactional
    public Course getReferenceById(Long aLong) {
        return courseRepository.getReferenceById(aLong);
    }

    public CourseDto toDto(Course course) {
        if(!Hibernate.isInitialized(course)) {
            return new CourseDto(course.getId(), null, null, null);
        }
        return new CourseDto(course.getId(), course.getName(), course.getDescription(), userService.toDto(course.getTeacher()));
    }

    public CourseAccessDto toDto(CourseAccess course) {
        return new CourseAccessDto(course.getCourse().getId(), course.getCourse().getName(),
                course.getCourse().getDescription(), userService.toDto(course.getCourse().getTeacher()), course.getStatus());
    }
}
