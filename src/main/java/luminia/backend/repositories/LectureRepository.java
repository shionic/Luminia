package luminia.backend.repositories;

import luminia.backend.models.Course;
import luminia.backend.models.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    @Query("select l from Lecture l where l.parent = ?1 and l.rank <= ?2")
    Page<Lecture> findAllByCourseAndRank(Course course, int rank, Pageable pageable);
}
