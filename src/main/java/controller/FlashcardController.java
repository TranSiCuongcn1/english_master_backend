package controller;

import dto.request.CreateFlashcardRequest;
import dto.response.FlashcardResponse;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import service.FlashcardService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/flashcards")
@RequiredArgsConstructor
public class FlashcardController {

    private final FlashcardService flashcardService;

    @GetMapping
    public ResponseEntity<List<FlashcardResponse>> getAll(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(flashcardService.getAll(user.getId()));
    }

    @PostMapping
    public ResponseEntity<FlashcardResponse> create(
            @AuthenticationPrincipal User user,
            @RequestBody CreateFlashcardRequest request
    ) {
        return ResponseEntity.ok(flashcardService.create(user, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal User user,
            @PathVariable String id
    ) {
        flashcardService.delete(user.getId(), UUID.fromString(id));
        return ResponseEntity.ok().build();
    }
}
