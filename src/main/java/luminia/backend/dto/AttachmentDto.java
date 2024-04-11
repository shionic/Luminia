package luminia.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class AttachmentDto {
    private Long id;
    private String fileName;
    private String path;
    private long size;
    private long user_id;
    private LocalDateTime uploadDate;
}
