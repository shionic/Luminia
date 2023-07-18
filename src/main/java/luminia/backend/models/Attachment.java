package luminia.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Attachment")
@Table(name = "attachments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attachments_gen")
    @SequenceGenerator(name = "attachments_gen", sequenceName = "attachments_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "file_name")
    private String fileName;
    private String path;
}
