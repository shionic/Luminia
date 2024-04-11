package luminia.backend.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import luminia.backend.dto.UserDto;
import luminia.backend.models.User;
import luminia.backend.repositories.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public Optional<User> findById(Long aLong) {
        return userRepository.findById(aLong);
    }

    public User getReferenceById(Long aLong) {
        return userRepository.getReferenceById(aLong);
    }

    /**
     * Return the user. Only for debug
     * @return Debug user
     */
    public CurrentUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return null;
        }
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return new CurrentUser(jwt.getClaim("userId"), authentication.getName(), jwt.getClaimAsStringList("roles"));
    }

    public UserDto toDto(User user) {
        if(!Hibernate.isInitialized(user)) {
            return new UserDto(user.getId(), null);
        }
        return new UserDto(user.getId(), user.getRating());
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public class CurrentUser {
        private Long id;
        private String username;
        private List<String> roles;

        @JsonIgnore
        public User getEntity() {
            return userRepository.getReferenceById(id);
        }
    }
}
