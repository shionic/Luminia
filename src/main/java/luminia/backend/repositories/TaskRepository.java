package luminia.backend.repositories;

import luminia.backend.models.Task;
import luminia.backend.models.TaskResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "select tasks.id, display_name, task, rank, deadline_date, important_date, COALESCE (status, 2) from tasks left join task_results on user_id=?2 and task_id=tasks.id and status != 4 where course_id=?1 and rank>=(select rank from course_access where course_id=?1 and user_id=?2) order by status",
            countQuery = "select count(*) from tasks where course_id=?1 and rank>=(select rank from course_access where course_id=?1 and user_id=?2)", nativeQuery = true)
    Page<TaskAndStatus> findAllByCourseAndUserWithSort(Long courseId, Long userId, Pageable pageable);
    @Query("select t from Task t join fetch t.attachments where t.id = ?1")
    Optional<Task> findByIdFetchAttachments(Long taskId);
    interface TaskAndStatus {
        Long getId();
        String getDisplayName();
        int getRank();
        Task.TaskType getTask();
        LocalDateTime getDeadlineDate();
        LocalDateTime getImportantDate();
        TaskResult.TaskStatus getStatus();
    }
}
