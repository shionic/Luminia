package luminia.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SelfUserDto {
    private UserDto user;
    private List<String> roles;
}
