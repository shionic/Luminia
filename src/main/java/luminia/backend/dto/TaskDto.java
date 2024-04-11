package luminia.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import luminia.backend.models.Task;
import luminia.backend.models.TaskResult;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class TaskDto {
    private Long id;
    private String displayName;
    private List<AttachmentDto> attachments;
    private Task.TaskType task;
    private TaskResult.TaskStatus status;
}
