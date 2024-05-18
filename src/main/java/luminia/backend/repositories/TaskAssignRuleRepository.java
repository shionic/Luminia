package luminia.backend.repositories;

import luminia.backend.models.TaskAssignRule;
import luminia.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskAssignRuleRepository extends JpaRepository<TaskAssignRule, Long> {
    @Query("select r.task.id as taskId, u.id as userId, r.variantStrategy, r.endDate from TaskAssignRule r, User u where (select a from CourseAccess a where a.user = u and a.course = r.course) IS NOT NULL and "+
    "(r.requireTask IS NULL OR (select tr from TaskResult tr where tr.author = u and tr.taskAssign = (select ta from TaskAssign ta where ta.target = u and ta.task = r.requireTask) and tr.status = 4) IS NOT NULL) and "+
    "(select ta from TaskAssign ta where ta.target = u and ta.task = r.task) IS NULL and " +
    "r.startDate >= ?1 and r.endDate < ?1")
    List<RuleReport> findAllMatchTime(LocalDateTime current);

    interface RuleReport {
        Long getUserId();
        Long getTaskId();
        TaskAssignRule.TaskAssignVariantStrategy getVariantStrategy();
        LocalDateTime getEndDate();
    }
}
