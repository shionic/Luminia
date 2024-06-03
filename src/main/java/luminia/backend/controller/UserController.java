package luminia.backend.controller;

import lombok.AllArgsConstructor;
import luminia.backend.dto.ListDto;
import luminia.backend.dto.SelfUserDto;
import luminia.backend.dto.UserDto;
import luminia.backend.exceptions.NotFoundException;
import luminia.backend.services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @GetMapping("/current")
    public SelfUserDto getCurrentUser() {
        var currentUser = userService.getUser();
        var user = userService.findById(currentUser.getId());
        if(user.isEmpty()) {
            user = Optional.of(userService.create(currentUser));
        }
        return new SelfUserDto(userService.toDto(user.get(), true), currentUser.getRoles());
    }

    @GetMapping("/by/id/{id}")
    public UserDto getById(@PathVariable long id) {
        var user = userService.findById(id);
        if(user.isEmpty()) {
            throw new NotFoundException("User");
        }
        return userService.toDto(user.get(), false);
    }

    @GetMapping("/by/username/{id}")
    public UserDto getByUsername(@PathVariable String username) {
        var user = userService.findByUsername(username);
        if(user.isEmpty()) {
            throw new NotFoundException("User");
        }
        return userService.toDto(user.get(), false);
    }

    @GetMapping("/by/rating")
    public ListDto<UserDto> getByRating(@RequestParam(name ="pageId", defaultValue ="0") int pageId) {
        var list = userService.findAll(PageRequest.of(pageId, 10, Sort.by(Sort.Direction.DESC, "rating")));
        return new ListDto<>(list.map(userService::toDto));
    }

    @GetMapping("/search")
    public ListDto<UserDto> search(@RequestParam(name ="name", defaultValue ="") String name, @RequestParam(name ="pageId", defaultValue ="0") int pageId) {
        //var user = userService.getUser();
        //var isPrivate = user.hasRole("ADMIN") || user.hasRole("TEACHER");
        var isPrivate = false;
        var list = userService.findByNameLike("%"+name+"%", PageRequest.of(pageId, 10, Sort.by(Sort.Direction.DESC, "rating")), isPrivate);
        return new ListDto<>(list.map(userService::toDto));
    }
}
