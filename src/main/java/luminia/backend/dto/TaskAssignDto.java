package luminia.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Setter;
import luminia.backend.models.TaskAssign;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
public class TaskAssignDto {
    private Long id;
    private String variant;
    private TaskAssign.TaskAssignType type;
    private LocalDateTime deadlineDate;
    private LocalDateTime importantDate;
    private TaskDto task;
    private UserDto target;
}
