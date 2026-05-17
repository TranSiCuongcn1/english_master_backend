package controller;

import dto.response.GrammarTopicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.GrammarService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/grammar")
@RequiredArgsConstructor
public class GrammarController {

    private final GrammarService grammarService;

    @GetMapping
    public ResponseEntity<List<GrammarTopicResponse>> getTopics() {
        return ResponseEntity.ok(grammarService.getTopics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrammarTopicResponse> getTopicById(@PathVariable String id) {
        return ResponseEntity.ok(grammarService.getTopicById(UUID.fromString(id)));
    }
}
