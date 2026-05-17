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
public class TestResponse {
    private String id;
    private String title;
    private String description;
    private String category;
    private Integer year;
    private Integer durationMinutes;
    private Integer totalQuestions;
    private List<QuestionResponse> questions;
}
