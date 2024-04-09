package luminia.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "Course")
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "courses_gen")
    @SequenceGenerator(name = "courses_gen", sequenceName = "courses_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Task> tasks;
}
