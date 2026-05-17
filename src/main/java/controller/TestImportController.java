package controller;

import dto.request.CreateTestRequest;
import dto.response.TestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.TestImportService;

@RestController
@RequestMapping("/api/admin/tests/import")
@RequiredArgsConstructor
public class TestImportController {

    private final TestImportService testImportService;

    @PostMapping
    public ResponseEntity<TestResponse> importTest(
            @RequestPart("testData") CreateTestRequest testData,
            @RequestPart("file") MultipartFile file) {
        
        TestResponse response = testImportService.importTestWithQuestions(testData, file);
        return ResponseEntity.ok(response);
    }
}
