package luminia.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "CourseAccess")
@Table(name = "course_access")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_access_gen")
    @SequenceGenerator(name = "course_access_gen", sequenceName = "course_access_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private int rank;
    private CourseAccessStatus status;

    public enum CourseAccessStatus {
        UNKNOWN, ACCESS, IN_WORK, COMPLETED
    }
}
