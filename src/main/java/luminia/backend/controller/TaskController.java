package luminia.backend.controller;

import lombok.AllArgsConstructor;
import luminia.backend.dto.ListDto;
import luminia.backend.dto.TaskDto;
import luminia.backend.dto.TaskResultDto;
import luminia.backend.exceptions.IllegalArgumentException;
import luminia.backend.exceptions.NotFoundException;
import luminia.backend.models.Attachment;
import luminia.backend.models.CourseAccess;
import luminia.backend.models.TaskResult;
import luminia.backend.models.User;
import luminia.backend.services.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private static final int TASK_PAGE_SIZE = 30;
    private TaskService taskService;
    private TaskResultService taskResultService;
    private UserService userService;
    private CourseService courseService;
    private AttachmentService attachmentService;
    private TaskAssignService assignService;

    @GetMapping("/by/course/{courseId}/page/{pageId}")
    public ListDto<TaskDto> findByCourse(@PathVariable Long courseId, @PathVariable int pageId) {
        if(pageId < 0) {
            throw new IllegalArgumentException("pageId cannot be less than 0");
        }
        var usr = userService.getUser();
        return new ListDto<>(taskService.findByCourseAndUserWithSort(courseId, usr.getId(), PageRequest.of(pageId, TASK_PAGE_SIZE)).map(taskService::toDto));
    }

    @GetMapping("/by/id/{id}")
    public TaskDto findById(@PathVariable long id) {
        var usr = userService.getUser();
        var e = taskService.findByIdFetchAttachments(id);
        if(e.isEmpty()) {
            throw new NotFoundException("Task not found");
        }
        var task = e.get();
        var access = courseService.findAccessByCourseAndUser(task.getCourse(), usr.getEntity());
        if(access.isEmpty()) {
            throw new SecurityException("Access denied");
        }
        return taskService.toDto(e.get());
    }

    @PostMapping("/by/id/{id}/upload")
    public TaskResultDto upload(@PathVariable long id, @RequestBody UploadTask request) {
        if(request.attachments == null || request.attachments.isEmpty()) {
            throw new IllegalArgumentException("Attachments is empty");
        }
        var usr = userService.getUser();
        var e = taskService.findByIdFetchAttachments(id);
        if(e.isEmpty()) {
            throw new NotFoundException("Task not found");
        }
        var task = e.get();
        var access = courseService.findAccessByCourseAndUser(task.getCourse(), usr.getEntity());
        if(access.isEmpty()) {
            throw new SecurityException("Access denied");
        }
        User target;
        boolean isTeacher;
        if(request.userId != null && !request.userId.equals(usr.getId())) {
            if(access.get().getStatus() != CourseAccess.CourseAccessStatus.OWNING) {
                throw new SecurityException("Access denied");
            }
            target = userService.getReferenceById(request.userId());
            isTeacher = true;
        } else {
            target = usr.getEntity();
            isTeacher = false;
        }
        var assign = assignService.findByTaskAndUser(task, target);
        if(assign.isEmpty()) {
            if(!isTeacher) {
                throw new SecurityException("Access denied");
            } else {
                throw new IllegalArgumentException("Task not assigned to target user");
            }
        }
        List<Attachment> attachments = attachmentService.findAllById(request.attachments);
        TaskResult result = new TaskResult();
        result.setTaskAssign(assign.get());
        result.setAuthor(usr.getEntity());
        result.setTarget(target);
        result.setStatus(isTeacher ? TaskResult.TaskStatus.ACCEPT : TaskResult.TaskStatus.WAIT);
        result.setAttachments(attachments);
        result.setUploadDate(LocalDateTime.now());
        result = taskResultService.save(result);
        return taskResultService.toDto(result);
    }

    public record UploadTask(Long userId, List<Long> attachments) {

    }
}
