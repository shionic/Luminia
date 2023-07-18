package luminia.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "TaskResult")
@Table(name = "task_results")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResult {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_results_gen")
    @SequenceGenerator(name = "task_results_gen", sequenceName = "task_results_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private TaskStatus status;

    enum TaskStatus {
        UNKNOWN
    }
}
