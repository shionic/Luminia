package luminia.backend.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "TaskAssignRule")
@Table(name = "task_assign_rules")
public class TaskAssignRule {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_assign_rules_gen")
    @SequenceGenerator(name = "task_assign_rules_gen", sequenceName = "task_assign_rules_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "require_task_id")
    private Task requireTask;
    @Column(name = "variant_strategy")
    private TaskAssignVariantStrategy variantStrategy;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    public enum TaskAssignVariantStrategy {
        NONE
    }
}
