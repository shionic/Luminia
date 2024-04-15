package luminia.backend.services;

import lombok.AllArgsConstructor;
import luminia.backend.dto.TaskAssignDto;
import luminia.backend.models.Task;
import luminia.backend.models.TaskAssign;
import luminia.backend.models.TaskResult;
import luminia.backend.models.User;
import luminia.backend.repositories.TaskAssignRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class TaskAssignService {
    private UserService userService;
    private TaskService taskService;
    private TaskAssignRepository repository;

    public Optional<TaskAssign> findByTaskAndUser(Task task, User user) {
        return repository.findByTaskAndTarget(task, user);
    }

    public Page<TaskAssign> findAllByUserAndStatus(User user, TaskResult.TaskStatus status, Pageable pageable) {
        return repository.findAllByUserAndStatus(user, status, pageable);
    }

    public TaskAssignDto toDto(TaskAssign t) {
        return toDto(t, true);
    }

    public TaskAssignDto toDto(TaskAssign t, boolean includeTask) {
        return new TaskAssignDto(t.getId(), t.getVariant(), t.getType(), t.getDeadlineDate(), t.getImportantDate(),
                includeTask ? taskService.toDto(t.getTask()) : null, userService.toDto(t.getTarget()));
    }
}
