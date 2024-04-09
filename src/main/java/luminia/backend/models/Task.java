package luminia.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tasks_attachments",
            joinColumns = @JoinColumn(name = "attachment_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private List<Attachment> attachments;
    private int rank;
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<TaskResult> results;
    private TaskType task;
    @Column(name = "deadline_date")
    private LocalDateTime deadlineDate;
    @Column(name = "important_date")
    private LocalDateTime importantDate;

    public enum TaskType {
        LECTURE, TASK
    }
}
