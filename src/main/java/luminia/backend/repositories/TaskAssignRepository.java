package luminia.backend.repositories;

import luminia.backend.models.Task;
import luminia.backend.models.TaskAssign;
import luminia.backend.models.TaskResult;
import luminia.backend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TaskAssignRepository extends JpaRepository<TaskAssign, Long> {
    Optional<TaskAssign> findByTaskAndTarget(Task task, User user);
    @Query("select t from TaskAssign t join fetch t.task join fetch t.task.course join fetch t.task.course.teacher "+
            "where t.target = ?1 and COALESCE( (select r.status from TaskResult r where r.target = ?1 and r.author = ?1 and r.status != 4), 2) = ?2")
    Page<TaskAssign> findAllByUserAndStatus(User user, TaskResult.TaskStatus status, Pageable pageable);
    @Query("select t from TaskAssign t join fetch t.task left join t.task.attachments join join fetch t.task.course join fetch t.task.course.teacher "+
            "where t.id = ?1")
    Optional<TaskAssign> findByIdAndUserFetchAll(Long id);
}
