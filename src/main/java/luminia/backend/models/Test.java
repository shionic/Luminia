package luminia.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Test")
@Table(name = "tests")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tests_gen")
    @SequenceGenerator(name = "tests_gen", sequenceName = "tests_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;
}
