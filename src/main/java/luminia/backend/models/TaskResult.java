package luminia.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    @JoinColumn(name = "target_id")
    private User target;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    private TaskStatus status;
    private int rating;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tasks_result_attachments",
            joinColumns = @JoinColumn(name = "attachment_id"),
            inverseJoinColumns = @JoinColumn(name = "task_result_id"))
    private List<Attachment> attachments;
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    public enum TaskStatus {
        REJECTED, STARTED, NOT_STARTED, WAIT, ACCEPT, ABSORBED
    }
}
