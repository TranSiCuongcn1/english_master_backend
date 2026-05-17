package controller;

import dto.response.TestResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.TestResultService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/results")
@RequiredArgsConstructor
public class AdminResultController {

    private final TestResultService testResultService;

    @GetMapping
    public ResponseEntity<List<TestResultResponse>> getAll() {
        return ResponseEntity.ok(testResultService.getAll());
    }
}
