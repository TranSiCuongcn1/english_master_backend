package repository;

import entity.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FlashCardRepository extends JpaRepository<FlashCard, UUID> {
    List<FlashCard> findByIsSystemTrueOrCreatedById(UUID userId);
}
