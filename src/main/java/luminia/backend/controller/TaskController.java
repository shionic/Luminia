package luminia.backend.controller;

import lombok.AllArgsConstructor;
import luminia.backend.dto.ListDto;
import luminia.backend.dto.TaskDto;
import luminia.backend.services.TaskService;
import luminia.backend.services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private static final int TASK_PAGE_SIZE = 30;
    private TaskService taskService;
    private UserService userService;

    @GetMapping("/by/course/{courseId}/page/{pageId}")
    public ListDto<TaskDto> findByCourse(@PathVariable Long courseId, @PathVariable int pageId) {
        var usr = userService.getUser();
        return new ListDto<>(taskService.findByCourseAndUserWithSort(courseId, usr.getId(), PageRequest.of(pageId, TASK_PAGE_SIZE)).map(taskService::toDto));
    }
}
