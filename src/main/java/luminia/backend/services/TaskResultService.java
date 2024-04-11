package luminia.backend.services;

import lombok.AllArgsConstructor;
import luminia.backend.dto.AttachmentDto;
import luminia.backend.dto.TaskResultDto;
import luminia.backend.models.Task;
import luminia.backend.models.TaskResult;
import luminia.backend.models.User;
import luminia.backend.repositories.TaskResultRepository;
import luminia.backend.utils.JpaUtils;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskResultService {
    private TaskResultRepository repository;
    private AttachmentService attachmentService;

    public Optional<TaskResult> findByUserAndTask(User user, Task task) {
        return repository.findByUserAndTask(user, task);
    }

    public Page<TaskResult> findAllByUserAndTask(User user, Task task, int page, int pageSize) {
        return repository.findAllByUserAndTask(user, task, PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.ASC, "uploadDate")));
    }

    public <S extends TaskResult> S save(S entity) {
        return repository.save(entity);
    }

    public TaskResultDto toDto(TaskResult e) {
        List<AttachmentDto> attachmentDtoList = JpaUtils.mapIfInitialized(e.getAttachments(), attachmentService::toDto);
        return new TaskResultDto(e.getId(), e.getTask().getId(), e.getTarget().getId(), e.getAuthor().getId(), e.getStatus(),
                e.getRating(), attachmentDtoList, e.getUploadDate());
    }
}
