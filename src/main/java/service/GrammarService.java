package service;

import dto.response.GrammarTopicResponse;
import entity.GrammarTopic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.GrammarTopicRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GrammarService {

    private final GrammarTopicRepository grammarTopicRepository;

    @Transactional(readOnly = true)
    public List<GrammarTopicResponse> getTopics() {
        return grammarTopicRepository.findAllByOrderByOrderIndexAsc().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GrammarTopicResponse getTopicById(UUID id) {
        GrammarTopic topic = grammarTopicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grammar topic not found"));
        return toResponse(topic);
    }

    private GrammarTopicResponse toResponse(GrammarTopic topic) {
        List<String> examples = topic.getExamples() != null
                ? topic.getExamples().stream().map(e -> e.getExampleText()).collect(Collectors.toList())
                : List.of();

        return GrammarTopicResponse.builder()
                .id(topic.getId().toString())
                .title(topic.getTitle())
                .content(topic.getContent())
                .examples(examples)
                .build();
    }
}
