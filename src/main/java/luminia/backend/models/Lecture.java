package luminia.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Lecture")
@Table(name = "lectures")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lectures_gen")
    @SequenceGenerator(name = "lectures_gen", sequenceName = "lectures_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "display_name")
    private String displayName;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Course parent;
    @ManyToOne
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;
    private String url;
    private int rank;
    private LectureStatus status;

    public enum LectureStatus {
        UNKNOWN, SKIPPED, VISITED
    }
}
