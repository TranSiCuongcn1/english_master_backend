package repository;

import entity.VocabularyTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VocabularyTopicRepository extends JpaRepository<VocabularyTopic, UUID> {
}
