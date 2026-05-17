package dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestRequest {
    private String title;
    private String description;
    private String category;  // "TOEIC", "IELTS", etc.
    private Integer year;
    private Integer durationMinutes;
    private Integer totalQuestions;
    private List<CreateQuestionRequest> questions;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateQuestionRequest {
        private String type;     // "multiple-choice" or "text"
        private String text;
        private List<String> options;
        private String correctAnswer;
        private String explanation;
        private Integer part;
        private String audioUrl;
        private String imageUrl;
        private String passageText;
        private String skill;    // "Listening", "Reading", etc.
    }
}
