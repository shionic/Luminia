package luminia.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import luminia.backend.models.Lecture;

@AllArgsConstructor
@Getter
public class LectureDto {
    private Long id;
    private String displayName;
    private Long parentId;
    private String url;
    private int rank;
    private Lecture.LectureStatus status;
}
