package luminia.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import luminia.backend.models.TaskResult;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class TaskResultDto {
    private Long id;
    private TaskAssignDto taskAssign;
    private UserDto target;
    private UserDto author;
    private TaskResult.TaskStatus status;
    private int rating;
    private List<AttachmentDto> attachments;
    private LocalDateTime uploadDate;
}
