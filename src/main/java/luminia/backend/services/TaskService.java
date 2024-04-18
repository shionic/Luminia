package luminia.backend.services;

import lombok.AllArgsConstructor;
import luminia.backend.dto.AttachmentDto;
import luminia.backend.dto.TaskDto;
import luminia.backend.models.Task;
import luminia.backend.repositories.TaskRepository;
import luminia.backend.utils.JpaUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private CourseService courseService;
    private TaskRepository taskRepository;
    private AttachmentService attachmentService;

    public Page<TaskRepository.TaskAndStatus> findByCourseAndUserWithSort(Long courseId, Long userId, Pageable pageable) {
        return taskRepository.findAllByCourseAndUserWithSort(courseId, userId, pageable);
    }

    public Optional<Task> findByIdFetchAttachments(Long taskId) {
        return taskRepository.findByIdFetchAttachments(taskId);
    }

    public Optional<Task> findById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    public Task getReferenceById(Long aLong) {
        return taskRepository.getReferenceById(aLong);
    }

    public TaskDto toDto(TaskRepository.TaskAndStatus e) {
        return new TaskDto(e.getId(), e.getDisplayName(), null, null, e.getTask(), e.getStatus());
    }

    public TaskDto toDto(Task e, boolean fetchAttachments) {
        List<AttachmentDto> attachmentDtoList = JpaUtils.mapIfInitialized(e.getAttachments(), attachmentService::toDto, fetchAttachments);
        return new TaskDto(e.getId(), e.getDisplayName(), courseService.toDto(e.getCourse()), attachmentDtoList, e.getTask(), null);
    }
}
