package luminia.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CourseDto {
    private Long id;
    private String name;
    private String description;
    private UserDto teacher;
}
