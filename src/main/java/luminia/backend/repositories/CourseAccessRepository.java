package luminia.backend.repositories;

import luminia.backend.models.CourseAccess;
import luminia.backend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseAccessRepository extends JpaRepository<CourseAccess, Long> {
    @Query("select a from CourseAccess a join fetch a.course where a.user = ?1")
    Page<CourseAccess> findByUser(User user, Pageable pageable);
}
