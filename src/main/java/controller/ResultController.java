package controller;

import dto.request.SubmitTestRequest;
import dto.response.TestResultResponse;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import service.TestResultService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultController {

    private final TestResultService testResultService;

    @PostMapping
    public ResponseEntity<TestResultResponse> submit(
            @AuthenticationPrincipal User user,
            @RequestBody SubmitTestRequest request
    ) {
        return ResponseEntity.ok(testResultService.submit(user, request));
    }

    @GetMapping("/me")
    public ResponseEntity<List<TestResultResponse>> getMyResults(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(testResultService.getMyResults(user.getId()));
    }

    @GetMapping("/me/{testId}")
    public ResponseEntity<TestResultResponse> getByTestId(
            @AuthenticationPrincipal User user,
            @PathVariable String testId
    ) {
        return ResponseEntity.ok(testResultService.getByTestId(user.getId(), UUID.fromString(testId)));
    }
}
