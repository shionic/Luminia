package luminia.backend.services;

import lombok.AllArgsConstructor;
import luminia.backend.dto.LectureDto;
import luminia.backend.models.Course;
import luminia.backend.models.Lecture;
import luminia.backend.repositories.LectureRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LectureService {
    private LectureRepository lectureRepository;

    public Optional<Lecture> findById(Long aLong) {
        return lectureRepository.findById(aLong);
    }

    public Page<Lecture> findAll(Course course, int rank, int pageId, int pageSize) {
        return lectureRepository.findAllByCourseAndRank(course, rank, PageRequest.of(pageId, pageSize, Sort.by("status", "rank")));
    }

    public LectureDto toDto(Lecture lecture) {
        return new LectureDto(lecture.getId(), lecture.getDisplayName(), lecture.getParent().getId(), lecture.getUrl(), lecture.getRank(), lecture.getStatus());
    }
}
