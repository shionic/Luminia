package luminia.backend.services;

import lombok.AllArgsConstructor;
import luminia.backend.dto.AttachmentDto;
import luminia.backend.dto.TaskDto;
import luminia.backend.models.Course;
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

    public Task createTask(String name, String description, Task.TaskType taskType, List<Long> attachments, List<Long> privateAttachments, Course course) {
        Task task = new Task();
        task.setDisplayName(name);
        task.setDescription(description);
        task.setTask(taskType);
        task.setCourse(course);
        task.setAttachments(attachments);
        task.setPrivateAttachments(privateAttachments);
        return taskRepository.save(task);
    }

    public Optional<Task> findById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    public Task getReferenceById(Long aLong) {
        return taskRepository.getReferenceById(aLong);
    }

    public TaskDto toDto(Task e, boolean fetchAttachments, boolean isPrivate) {
        List<AttachmentDto> attachmentDtoList;
        if(fetchAttachments && e.getAttachments() != null) {
            attachmentDtoList = attachmentService.findAllById(e.getAttachments()).stream().map(attachmentService::toDto).toList();
        } else {
            attachmentDtoList = List.of();
        }
        List<AttachmentDto> privateAttachmentDtoList;
        if(fetchAttachments && isPrivate && e.getPrivateAttachments() != null) {
            privateAttachmentDtoList = attachmentService.findAllById(e.getPrivateAttachments()).stream().map(attachmentService::toDto).toList();
        } else {
            privateAttachmentDtoList = List.of();
        }
        return new TaskDto(e.getId(), e.getDisplayName(), e.getDescription(),
                courseService.toDto(e.getCourse()), attachmentDtoList, privateAttachmentDtoList, e.getTask(), null);
    }
}
