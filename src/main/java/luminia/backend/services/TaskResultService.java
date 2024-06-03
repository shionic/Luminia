package luminia.backend.services;

import lombok.AllArgsConstructor;
import luminia.backend.dto.AttachmentDto;
import luminia.backend.dto.TaskResultDto;
import luminia.backend.models.Task;
import luminia.backend.models.TaskAssign;
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
    private TaskAssignService taskAssignService;
    private UserService userService;

    public Optional<TaskResult> findByUserAndTask(User user, TaskAssign taskAssign, boolean self) {
        if(self) {
            return repository.findByUserAndTask(user, taskAssign);
        } else {
            return repository.findByUserAndTaskTeacher(user, taskAssign);
        }
    }

    public Page<TaskResult> findAllByUserAndTask(User user, TaskAssign taskAssign, int page, int pageSize) {
        return repository.findAllByUserAndTask(user, taskAssign, PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.ASC, "uploadDate")));
    }

    public <S extends TaskResult> S save(S entity) {
        return repository.save(entity);
    }

    public TaskResultDto toDto(TaskResult e, boolean fetchAttachments) {
        List<AttachmentDto> attachmentDtoList;
        if(fetchAttachments) {
            attachmentDtoList = attachmentService.findAllById(e.getAttachments()).stream().map(attachmentService::toDto).toList();
        } else {
            attachmentDtoList = List.of();
        }
        return new TaskResultDto(e.getId(), taskAssignService.toDto(e.getTaskAssign(), false, false), userService.toDto(e.getTarget()),
                userService.toDto(e.getAuthor()), e.getStatus(), e.getRating(), attachmentDtoList, e.getUploadDate());
    }
}
