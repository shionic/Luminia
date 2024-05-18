package luminia.backend.controller;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import luminia.backend.dto.ListDto;
import luminia.backend.dto.TaskAssignDto;
import luminia.backend.dto.TaskDto;
import luminia.backend.dto.TaskResultDto;
import luminia.backend.exceptions.IllegalArgumentException;
import luminia.backend.exceptions.NotFoundException;
import luminia.backend.models.*;
import luminia.backend.services.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/taskassign")
public class TaskAssignController {
    private static final int TASK_PAGE_SIZE = 30;
    private TaskService taskService;
    private TaskResultService taskResultService;
    private TaskAssignService taskAssignService;
    private UserService userService;
    private CourseService courseService;
    private AttachmentService attachmentService;
    private TaskAssignService assignService;

    @GetMapping("/by/status/{status}")
    public ListDto<TaskAssignDto> findByStatus(@PathVariable TaskResult.TaskStatus status,
                                               @RequestParam(name ="pageId", defaultValue ="0") int pageId,
                                               @RequestParam(name = "courseId", defaultValue = "-1") long courseId) {
        if(pageId < 0) {
            throw new IllegalArgumentException("pageId cannot be less than 0");
        }
        var usr = userService.getUser();
        Page<TaskAssign> r;
        if(courseId < 0) {
            r = taskAssignService.findAllByUserAndStatus(usr.getEntity(), status,
                    PageRequest.of(pageId, 10));
        } else {
            r = taskAssignService.findAllByUserAndStatusAndCourse(usr.getEntity(), status, courseService.getReferenceById(courseId),
                    PageRequest.of(pageId, 10));
        }
        return new ListDto<>(r.map(taskAssignService::toDto));
    }

    @GetMapping("/by/id/{id}")
    public TaskAssignDto findById(@PathVariable long id) {
        var usr = userService.getUser();
        var e = taskAssignService.findByIdAndUserFetchAll(id);
        if(e.isEmpty()) {
            throw new NotFoundException("TaskAssign not found");
        }
        return taskAssignService.toDto(e.get(), true);
    }

    @GetMapping("/by/id/{id}/result")
    public TaskResultDto findResultById(@PathVariable long id, @RequestParam(value = "self", defaultValue = "true") boolean self) {
        var usr = userService.getUser();
        var e = taskResultService.findByUserAndTask(usr.getEntity(), taskAssignService.getReferenceById(id), self);
        if(e.isEmpty()) {
            throw new NotFoundException("TaskResult not found");
        }
        return taskResultService.toDto(e.get(), true);
    }

    @PostMapping("/by/id/{id}/upload")
    public TaskResultDto upload(@PathVariable long id, @RequestBody UploadTask request) {
        if(request.attachments == null || request.attachments.isEmpty()) {
            throw new IllegalArgumentException("Attachments is empty");
        }
        var usr = userService.getUser();
        var e = taskService.findById(id);
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
        //List<Attachment> attachments = attachmentService.findAllById(request.attachments);
        TaskResult result = new TaskResult();
        result.setTaskAssign(assign.get());
        result.setAuthor(usr.getEntity());
        result.setTarget(target);
        result.setStatus(isTeacher ? TaskResult.TaskStatus.ACCEPT : TaskResult.TaskStatus.WAIT);
        result.setAttachments(request.attachments);
        result.setUploadDate(LocalDateTime.now());
        result = taskResultService.save(result);
        return taskResultService.toDto(result, false);
    }

    public record UploadTask(Long userId, List<Long> attachments) {

    }
}
