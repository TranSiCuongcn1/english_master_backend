package repository;

import entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findByTestIdOrderByOrderIndexAsc(UUID testId);
    List<Question> findByReadingPassageIdOrderByOrderIndexAsc(UUID readingPassageId);
}
