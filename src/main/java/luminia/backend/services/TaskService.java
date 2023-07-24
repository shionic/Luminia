package luminia.backend.services;

import lombok.AllArgsConstructor;
import luminia.backend.dto.TaskDto;
import luminia.backend.repositories.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskService {
    private TaskRepository taskRepository;

    public Page<TaskRepository.TaskAndStatus> findByCourseAndUserWithSort(Long courseId, Long userId, Pageable pageable) {
        return taskRepository.findByCourseAndUserWithSort(courseId, userId, pageable);
    }

    public TaskDto toDto(TaskRepository.TaskAndStatus e) {
        return new TaskDto(e.getId(), e.getDisplayName(), e.getAttachmentId(), e.getRank(), e.getStatus());
    }
}
