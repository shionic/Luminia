package luminia.backend.controller;

import lombok.AllArgsConstructor;
import luminia.backend.dto.UserDto;
import luminia.backend.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @GetMapping("/current")
    public UserDto getCurrentUser() {
        return userService.toDto(userService.getUser());
    }
}
