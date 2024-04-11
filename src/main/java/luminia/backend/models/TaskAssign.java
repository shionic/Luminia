package luminia.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "TaskAssign")
@Table(name = "task_assigns")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskAssign {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_assigns_gen")
    @SequenceGenerator(name = "task_assigns_gen", sequenceName = "task_assigns_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id")
    private User target;
    private String variant;
    private TaskAssignType type;
    @Column(name = "deadline_date")
    private LocalDateTime deadlineDate;
    @Column(name = "important_date")
    private LocalDateTime importantDate;

    public enum TaskAssignType {
        AUTOMATIC, MANUAL
    }
}
