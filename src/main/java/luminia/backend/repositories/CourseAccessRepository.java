package luminia.backend.repositories;

import luminia.backend.models.CourseAccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseAccessRepository extends JpaRepository<CourseAccess, Long> {
}
