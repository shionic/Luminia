package luminia.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import luminia.backend.models.TaskResult;

@AllArgsConstructor
@Getter
public class TaskDto {
    private Long id;
    private String displayName;
    private Long attachmentId;
    private int rank;
    private TaskResult.TaskStatus status;
}
