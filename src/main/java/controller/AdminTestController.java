package controller;

import dto.request.CreateTestRequest;
import dto.response.TestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TestService;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/tests")
@RequiredArgsConstructor
public class AdminTestController {

    private final TestService testService;

    @PostMapping
    public ResponseEntity<TestResponse> create(@RequestBody CreateTestRequest request) {
        return ResponseEntity.ok(testService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestResponse> update(@PathVariable String id, @RequestBody CreateTestRequest request) {
        return ResponseEntity.ok(testService.update(UUID.fromString(id), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        testService.delete(UUID.fromString(id));
        return ResponseEntity.ok().build();
    }

    // =========================================
    // QUESTION MANAGEMENT
    // =========================================

    @PostMapping("/{testId}/questions")
    public ResponseEntity<dto.response.QuestionResponse> addQuestion(
            @PathVariable String testId, 
            @RequestBody CreateTestRequest.CreateQuestionRequest request) {
        return ResponseEntity.ok(testService.addQuestionToTest(UUID.fromString(testId), request));
    }

    @PutMapping("/questions/{questionId}")
    public ResponseEntity<dto.response.QuestionResponse> updateQuestion(
            @PathVariable String questionId, 
            @RequestBody CreateTestRequest.CreateQuestionRequest request) {
        return ResponseEntity.ok(testService.updateQuestion(UUID.fromString(questionId), request));
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String questionId) {
        testService.deleteQuestion(UUID.fromString(questionId));
        return ResponseEntity.ok().build();
    }
}
