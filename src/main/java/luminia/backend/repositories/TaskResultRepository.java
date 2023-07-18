package luminia.backend.repositories;

import luminia.backend.models.Lecture;
import luminia.backend.models.Task;
import luminia.backend.models.TaskResult;
import luminia.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskResultRepository extends JpaRepository<TaskResult, Long> {
    Optional<TaskResult> findByUserAndTask(User user, Task task);
}
