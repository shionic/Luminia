package luminia.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import luminia.backend.models.Course;
import luminia.backend.models.Task;
import luminia.backend.models.TaskResult;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class TaskDto {
    private Long id;
    private String displayName;
    private String description;
    private CourseDto course;
    private List<AttachmentDto> attachments;
    private List<AttachmentDto> privateAttachments;
    private Task.TaskType task;
    private TaskResult.TaskStatus status;
}
