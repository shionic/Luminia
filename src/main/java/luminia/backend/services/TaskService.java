package luminia.backend.services;

import lombok.AllArgsConstructor;
import luminia.backend.dto.AttachmentDto;
import luminia.backend.dto.TaskDto;
import luminia.backend.models.Task;
import luminia.backend.repositories.TaskRepository;
import luminia.backend.utils.JpaUtils;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private TaskRepository taskRepository;
    private AttachmentService attachmentService;

    public Page<TaskRepository.TaskAndStatus> findByCourseAndUserWithSort(Long courseId, Long userId, Pageable pageable) {
        return taskRepository.findAllByCourseAndUserWithSort(courseId, userId, pageable);
    }

    public Optional<Task> findByIdFetchAttachments(Long taskId) {
        return taskRepository.findByIdFetchAttachments(taskId);
    }

    public Task getReferenceById(Long aLong) {
        return taskRepository.getReferenceById(aLong);
    }

    public TaskDto toDto(TaskRepository.TaskAndStatus e) {
        return new TaskDto(e.getId(), e.getDisplayName(), null, e.getRank(), e.getTask(),
                e.getDeadlineDate(), e.getImportantDate(), e.getStatus());
    }

    public TaskDto toDto(Task e) {
        List<AttachmentDto> attachmentDtoList = JpaUtils.mapIfInitialized(e.getAttachments(), attachmentService::toDto);
        return new TaskDto(e.getId(), e.getDisplayName(), attachmentDtoList, e.getRank(), e.getTask(),
                e.getDeadlineDate(), e.getImportantDate(), null);
    }
}
