package luminia.backend.repositories;

import luminia.backend.models.Task;
import luminia.backend.models.TaskAssign;
import luminia.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskAssignRepository extends JpaRepository<TaskAssign, Long> {
    Optional<TaskAssign> findByTaskAndTarget(Task task, User user);
}
