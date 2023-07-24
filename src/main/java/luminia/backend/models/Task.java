package luminia.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "Task")
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_gen")
    @SequenceGenerator(name = "tasks_gen", sequenceName = "tasks_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "display_name", unique = true)
    private String displayName;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Course parent;
    @ManyToOne
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;
    private int rank;
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<TaskResult> results;
}
