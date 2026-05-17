package repository;

import entity.ReadingPassage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReadingPassageRepository extends JpaRepository<ReadingPassage, UUID> {
    List<ReadingPassage> findByDifficulty(String difficulty);
    List<ReadingPassage> findByTitleContainingIgnoreCase(String keyword);
    List<ReadingPassage> findByDifficultyAndTitleContainingIgnoreCase(String difficulty, String keyword);
}
