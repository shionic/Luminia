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

    public TaskAssign getReferenceById(Long aLong) {
        return repository.getReferenceById(aLong);
    }

    public Optional<TaskAssign> findByTaskAndUser(Task task, User user) {
        return repository.findByTaskAndTarget(task, user);
    }

    public Page<TaskAssign> findAllByUserAndStatus(User user, TaskResult.TaskStatus status, Pageable pageable) {
        return repository.findAllByUserAndStatus(user, status, pageable);
    }

    public Optional<TaskAssign> findByIdAndUserFetchAll(Long id) {
        return repository.findByIdAndUserFetchAll(id);
    }

    public TaskAssignDto toDto(TaskAssign t) {
        return toDto(t, false);
    }

    public TaskAssignDto toDto(TaskAssign t, boolean fetchAttachments) {
        return new TaskAssignDto(t.getId(), t.getVariant(), t.getType(), t.getDeadlineDate(), t.getImportantDate(),
                taskService.toDto(t.getTask(), fetchAttachments), userService.toDto(t.getTarget()));
    }
}
