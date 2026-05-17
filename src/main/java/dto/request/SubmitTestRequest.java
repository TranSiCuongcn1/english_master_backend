package dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitTestRequest {
    private String testId;
    private Integer timeTakenSeconds;
    private List<AnswerItem> answers;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnswerItem {
        private String questionId;
        private String selectedAnswer;
    }
}
