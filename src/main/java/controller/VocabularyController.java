package controller;

import dto.response.VocabularyTopicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.VocabularyService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vocabulary")
@RequiredArgsConstructor
public class VocabularyController {

    private final VocabularyService vocabularyService;

    @GetMapping
    public ResponseEntity<List<VocabularyTopicResponse>> getTopics() {
        return ResponseEntity.ok(vocabularyService.getTopics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VocabularyTopicResponse> getTopicById(@PathVariable String id) {
        return ResponseEntity.ok(vocabularyService.getTopicById(UUID.fromString(id)));
    }
}
