package luminia.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseAccessDto extends CourseDto {
    private int rank;
    public CourseAccessDto(Long id, String name, String description, int rank) {
        super(id, name, description);
        this.rank = rank;
    }
}
