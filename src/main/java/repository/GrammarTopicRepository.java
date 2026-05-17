package repository;

import entity.GrammarTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GrammarTopicRepository extends JpaRepository<GrammarTopic, UUID> {
    List<GrammarTopic> findAllByOrderByOrderIndexAsc();
}
