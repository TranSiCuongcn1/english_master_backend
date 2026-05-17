package dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestResultResponse {
    private String testId;
    private Integer score;
    private Integer totalQuestions;
    private Integer timeTakenSeconds;
    private List<UserAnswerResponse> answers;
    private String date;
    private String userEmail;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserAnswerResponse {
        private String questionId;
        private String selectedAnswer;
        private Boolean isCorrect;
    }
}
