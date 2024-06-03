package luminia.backend.services;

import lombok.AllArgsConstructor;
import luminia.backend.dto.TaskAssignDto;
import luminia.backend.models.*;
import luminia.backend.repositories.TaskAssignRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public <S extends TaskAssign> S save(S entity) {
        return repository.save(entity);
    }

    public <S extends TaskAssign> List<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    public Page<TaskAssign> findAllByTask(Task task, Pageable pageable) {
        return repository.findAllByTask(task, pageable);
    }
    public Page<TaskAssign> findAllByUserAndStatusAndCourse(User user, TaskResult.TaskStatus status, Course course, Pageable pageable) {
        return repository.findAllByUserAndStatusAndCourse(user, status, course, pageable);
    }

    public TaskAssignDto toDto(TaskAssign t) {
        return toDto(t, false, false);
    }

    public TaskAssignDto toDto(TaskAssign t, boolean fetchAttachments, boolean isPrivate) {
        return new TaskAssignDto(t.getId(), t.getVariant(), t.getType(), t.getDeadlineDate(), t.getImportantDate(),
                taskService.toDto(t.getTask(), fetchAttachments, isPrivate), userService.toDto(t.getTarget()));
    }
}
