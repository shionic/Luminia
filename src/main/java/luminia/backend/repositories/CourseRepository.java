package luminia.backend.repositories;

import luminia.backend.models.Course;
import luminia.backend.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select c from Course c join fetch c.tasks where c.id = ?1")
    Optional<Course> findWithFetchAll(Long courseId);
}
