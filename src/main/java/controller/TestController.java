package controller;

import dto.response.TestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TestService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tests")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping
    public ResponseEntity<List<TestResponse>> getAll(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer year
    ) {
        return ResponseEntity.ok(testService.getAll(category, year));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(testService.getById(UUID.fromString(id)));
    }
}
