package service;

import dto.request.CreateFlashcardRequest;
import dto.response.FlashcardResponse;
import entity.FlashCard;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.FlashCardRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlashcardService {

    private final FlashCardRepository flashCardRepository;

    @Transactional(readOnly = true)
    public List<FlashcardResponse> getAll(UUID userId) {
        return flashCardRepository.findByIsSystemTrueOrCreatedById(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public FlashcardResponse create(User user, CreateFlashcardRequest request) {
        FlashCard card = new FlashCard();
        card.setFront(request.getFront());
        card.setBack(request.getBack());
        card.setExample(request.getExample());
        card.setCategory(request.getCategory());
        card.setIsSystem(false);
        card.setCreatedBy(user);
        return toResponse(flashCardRepository.save(card));
    }

    @Transactional
    public void delete(UUID userId, UUID cardId) {
        FlashCard card = flashCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Flashcard not found"));
        // Only allow deletion of user's own cards, not system cards
        if (card.getIsSystem()) {
            throw new RuntimeException("Cannot delete system flashcard");
        }
        if (card.getCreatedBy() != null && !card.getCreatedBy().getId().equals(userId)) {
            throw new RuntimeException("Cannot delete another user's flashcard");
        }
        flashCardRepository.delete(card);
    }

    private FlashcardResponse toResponse(FlashCard card) {
        return FlashcardResponse.builder()
                .id(card.getId().toString())
                .front(card.getFront())
                .back(card.getBack())
                .example(card.getExample())
                .category(card.getCategory())
                .isSystem(card.getIsSystem())
                .createdBy(card.getCreatedBy() != null ? card.getCreatedBy().getId().toString() : null)
                .build();
    }
}
