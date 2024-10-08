package luminia.backend.repositories;

import luminia.backend.models.Task;
import luminia.backend.models.TaskAssign;
import luminia.backend.models.TaskResult;
import luminia.backend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskResultRepository extends JpaRepository<TaskResult, Long> {
    @Query("select tr from TaskResult tr where tr.target = ?1 and tr.author = ?1 and tr.taskAssign = ?2 and tr.status != 4")
    Optional<TaskResult> findByUserAndTask(User user, TaskAssign task);
    @Query("select tr from TaskResult tr where tr.target = ?1 and tr.author != ?1 and tr.taskAssign = ?2 and tr.status != 4")
    Optional<TaskResult> findByUserAndTaskTeacher(User user, TaskAssign task);
    @Query("select tr from TaskResult tr where tr.target = ?1 and tr.taskAssign = ?2")
    Page<TaskResult> findAllByUserAndTask(User user, TaskAssign task, Pageable pageable);
}
