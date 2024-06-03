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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
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

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Page<User> findByNameLike(String search, Pageable pageable, boolean isPrivate) {
        if(!isPrivate) {
            return userRepository.findByNameLikeWithPrivacy(search, pageable);
        } else {
            return userRepository.findByNameLike(search, pageable);
        }
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

    public User create(CurrentUser currentUser) {
        User user = new User();
        user.setRating(0.0);
        user.setUsername(String.format("uid%d", currentUser.getId()));
        user.setNameProtected(true);
        user.setSocialProtected(true);
        return userRepository.save(user);
    }

    public UserDto toDto(User user) {
        return toDto(user, false);
    }

    public UserDto toDto(User user, boolean isPrivate) {
        if(!Hibernate.isInitialized(user)) {
            return new UserDto(user.getId(), null, null, null, null, null, true, true);
        }
        return new UserDto(user.getId(), user.getRating(), user.getUsername(),
                user.isNameProtected() && !isPrivate ? null : user.getFullName(),
                user.isSocialProtected() && !isPrivate ? null : user.getDiscordId(),
                user.isSocialProtected() && !isPrivate ? null : user.getSocialUsername(),
                user.isNameProtected(), user.isSocialProtected());
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

        @JsonIgnore
        public boolean hasRole(String role) {
            return roles.contains(role);
        }
    }
}
