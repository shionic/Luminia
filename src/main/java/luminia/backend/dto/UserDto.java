package luminia.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private Double rating;
    private String username;
    private String fullName;
    //
    private Long discordId;
    private String socialUsername;
    //
    private boolean nameProtected;
    private boolean socialProtected;
}
