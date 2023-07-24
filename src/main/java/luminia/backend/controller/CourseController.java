package luminia.backend.controller;

import lombok.AllArgsConstructor;
import luminia.backend.dto.CourseDto;
import luminia.backend.dto.ListDto;
import luminia.backend.exceptions.IllegalArgumentException;
import luminia.backend.exceptions.NotFoundException;
import luminia.backend.services.CourseService;
import luminia.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private static final int COURSE_PAGE_SIZE = 30;
    private CourseService courseService;
    private UserService userService;
    @GetMapping("/by/id/{id}")
    public CourseDto findById(@PathVariable Long id) {
        var o = courseService.findById(id);
        if(o.isEmpty()) {
            throw new NotFoundException("Course");
        }
        return courseService.toDto(o.get());
    }

    @GetMapping("/by/user/{pageId}")
    public ListDto<CourseDto> findByUser(@PathVariable int pageId) {
        if(pageId < 0) {
            throw new IllegalArgumentException("pageId cannot be less than 0");
        }
        var usr = userService.getUser();
        return new ListDto<>(courseService.findByUserWithSort(usr, pageId, COURSE_PAGE_SIZE).map(courseService::toDto));
    }
}
