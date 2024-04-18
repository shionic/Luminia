package luminia.backend.services;

import lombok.AllArgsConstructor;
import luminia.backend.dto.AttachmentDto;
import luminia.backend.models.Attachment;
import luminia.backend.models.User;
import luminia.backend.repositories.AttachmentRepository;
import luminia.backend.utils.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AttachmentService {
    private AttachmentRepository repository;
    private S3Service s3Service;

    public Optional<Attachment> findById(Long aLong) {
        return repository.findById(aLong);
    }

    public <S extends Attachment> S save(S entity) {
        return repository.save(entity);
    }

    public Attachment getReferenceById(Long aLong) {
        return repository.getReferenceById(aLong);
    }

    public List<Attachment> findAllById(Iterable<Long> longs) {
        return repository.findAllById(longs);
    }

    public Attachment create(User user, String fileName, String contentType, byte[] bytes) {
        Attachment attachment = new Attachment();
        attachment.setFileName(fileName);
        attachment.setPath(StringUtils.generateRandomString(16));
        attachment.setUser(user);
        attachment.setSize(bytes.length);
        attachment.setUploadDate(LocalDateTime.now());
        s3Service.upload(attachment.getPath(), contentType, bytes);
        return repository.save(attachment);
    }

    public AttachmentDto toDto(Attachment a) {
        if(!Hibernate.isInitialized(a)) {
            return new AttachmentDto(a.getId(), null, null, 0, -1, null);
        }
        return new AttachmentDto(a.getId(), a.getFileName(), s3Service.getUrl(a.getPath()).toString(), a.getSize(), a.getUser().getId(), a.getUploadDate());
    }
}
