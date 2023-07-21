package luminia.backend.repositories;

import luminia.backend.models.Lecture;
import luminia.backend.models.Task;
import luminia.backend.models.TaskResult;
import luminia.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TaskResultRepository extends JpaRepository<TaskResult, Long> {
    @Query("select tr from TaskResult tr where tr.user = ?1 and tr.task = ?2 and tr.status != 4")
    Optional<TaskResult> findByUserAndTask(User user, Task task);
}
