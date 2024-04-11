package luminia.backend.services;

import luminia.backend.dto.AttachmentDto;
import luminia.backend.models.Attachment;
import luminia.backend.repositories.AttachmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {
    private AttachmentRepository repository;

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

    public AttachmentDto toDto(Attachment a) {
        return new AttachmentDto(a.getId(), a.getFileName(), a.getPath(), a.getSize(), a.getUser().getId(), a.getUploadDate());
    }
}
