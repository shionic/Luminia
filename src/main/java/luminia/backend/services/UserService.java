package luminia.backend.services;

import lombok.AllArgsConstructor;
import luminia.backend.dto.UserDto;
import luminia.backend.models.User;
import luminia.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    /**
     * Return the user. Only for debug
     * @return Debug user
     */
    public User getUser() {
        return userRepository.findById(1L).orElseThrow();
    }

    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getRating());
    }
}
