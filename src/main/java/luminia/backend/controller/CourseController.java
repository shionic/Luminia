package luminia.backend.controller;

import lombok.AllArgsConstructor;
import luminia.backend.dto.CourseAccessDto;
import luminia.backend.dto.CourseDto;
import luminia.backend.dto.ListDto;
import luminia.backend.dto.TaskDto;
import luminia.backend.exceptions.IllegalArgumentException;
import luminia.backend.exceptions.NotFoundException;
import luminia.backend.models.Attachment;
import luminia.backend.models.Task;
import luminia.backend.services.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private static final int COURSE_PAGE_SIZE = 30;
    private AttachmentService attachmentService;
    private CourseService courseService;
    private TaskService taskService;
    private UserService userService;

    @PostMapping("/create")
    public CourseAccessDto create(@RequestBody CourseCreate request) {
        var user = userService.getUser();
        var course = courseService.create(request.name(), request.description(), user.getEntity());
        return courseService.toDto(course);
    }

    @PostMapping("/by/id/{id}/newtask")
    public TaskDto createTask(@PathVariable Long id, @RequestBody TaskCreate request) {
        var user = userService.getUser();
        var o = courseService.findById(id);
        if(o.isEmpty()) {
            throw new NotFoundException("Course");
        }
        verifyAttachments(request.attachments(), user.getId());
        verifyAttachments(request.privateAttachments(), user.getId());
        var task = taskService.createTask(request.name(), request.description(), request.type(), request.attachments(), request.privateAttachments(), o.get());
        return taskService.toDto(task, false, false);
    }

    @GetMapping("/by/id/{id}")
    public CourseDto findById(@PathVariable Long id) {
        var o = courseService.findWithFetchAll(id);
        if(o.isEmpty()) {
            throw new NotFoundException("Course");
        }
        return courseService.toDto(o.get());
    }

    @GetMapping("/by/user")
    public ListDto<CourseAccessDto> findByUser(@RequestParam(name ="pageId", defaultValue ="0") int pageId) {
        if(pageId < 0) {
            throw new IllegalArgumentException("pageId cannot be less than 0");
        }
        var usr = userService.getUser();
        return new ListDto<>(courseService.findByUserWithSort(usr.getEntity(), pageId, COURSE_PAGE_SIZE).map(courseService::toDto));
    }

    private void verifyAttachments(List<Long> userAttachments, Long userId) {
        List<Attachment> attachments = attachmentService.findAllById(userAttachments);
        if(attachments.size() != userAttachments.size()) {
            throw new IllegalArgumentException("Not all attachments found");
        }
        for(var a : attachments) {
            if(!a.getUser().getId().equals(userId)) {
                throw new IllegalArgumentException(String.format("You are not owning attachment %d", a.getId()));
            }
        }
    }

    public record CourseCreate(String name, String description) {

    }

    public record TaskCreate(String name, String description, Task.TaskType type, List<Long> attachments, List<Long> privateAttachments) {

    }
}
