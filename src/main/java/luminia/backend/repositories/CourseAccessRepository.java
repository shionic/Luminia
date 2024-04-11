package luminia.backend.repositories;

import luminia.backend.models.Course;
import luminia.backend.models.CourseAccess;
import luminia.backend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CourseAccessRepository extends JpaRepository<CourseAccess, Long> {
    @Query("select a from CourseAccess a join fetch a.course where a.user = ?1")
    Page<CourseAccess> findByUserFetchCourse(User user, Pageable pageable);
    @Query("select a from CourseAccess a where a.course = ?1 and a.user = ?2")
    Optional<CourseAccess> findByCourseAndUser(Course course, User user);
}
