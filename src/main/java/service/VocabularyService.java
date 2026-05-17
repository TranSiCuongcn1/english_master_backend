package service;

import dto.response.VocabularyTopicResponse;
import entity.VocabularyTopic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.VocabularyTopicRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VocabularyService {

    private final VocabularyTopicRepository vocabularyTopicRepository;

    @Transactional(readOnly = true)
    public List<VocabularyTopicResponse> getTopics() {
        return vocabularyTopicRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VocabularyTopicResponse getTopicById(UUID id) {
        VocabularyTopic topic = vocabularyTopicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vocabulary topic not found"));
        return toResponse(topic);
    }

    private VocabularyTopicResponse toResponse(VocabularyTopic topic) {
        List<VocabularyTopicResponse.WordResponse> words = topic.getWords() != null
                ? topic.getWords().stream().map(w -> VocabularyTopicResponse.WordResponse.builder()
                    .word(w.getWord())
                    .type(w.getType())
                    .meaning(w.getMeaning())
                    .example(w.getExample())
                    .build())
                .collect(Collectors.toList())
                : List.of();

        return VocabularyTopicResponse.builder()
                .id(topic.getId().toString())
                .title(topic.getTitle())
                .description(topic.getDescription())
                .words(words)
                .build();
    }
}
