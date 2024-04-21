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

    public Optional<Task> findById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    public Task getReferenceById(Long aLong) {
        return taskRepository.getReferenceById(aLong);
    }

    public TaskDto toDto(Task e, boolean fetchAttachments) {
        List<AttachmentDto> attachmentDtoList;
        if(fetchAttachments) {
            attachmentDtoList = attachmentService.findAllById(e.getAttachments()).stream().map(attachmentService::toDto).toList();
        } else {
            attachmentDtoList = List.of();
        }
        return new TaskDto(e.getId(), e.getDisplayName(), courseService.toDto(e.getCourse()), attachmentDtoList, e.getTask(), null);
    }
}
