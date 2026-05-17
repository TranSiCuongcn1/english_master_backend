package controller;

import dto.response.ReadingPassageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ReadingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reading")
@RequiredArgsConstructor
public class ReadingController {

    private final ReadingService readingService;

    @GetMapping
    public ResponseEntity<List<ReadingPassageResponse>> getPassages(
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String search
    ) {
        return ResponseEntity.ok(readingService.getPassages(difficulty, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadingPassageResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(readingService.getById(UUID.fromString(id)));
    }
}
