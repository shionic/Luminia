package luminia.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import luminia.backend.models.CourseAccess;

@Getter
@Setter
public class CourseAccessDto extends CourseDto {
    private int rank;
    private CourseAccess.CourseAccessStatus status;
    public CourseAccessDto(Long id, String name, String description, int rank, CourseAccess.CourseAccessStatus status) {
        super(id, name, description);
        this.rank = rank;
        this.status = status;
    }
}
