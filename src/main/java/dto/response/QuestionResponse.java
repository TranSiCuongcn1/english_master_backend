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
public class QuestionResponse {
    private String id;
    private String type;       // "multiple-choice" or "text"
    private String text;
    private List<String> options;
    private String correctAnswer;
    private String explanation;
    private Integer part;
    private String audioUrl;
    private String imageUrl;
    private String passageText;
    private String skill;      // "Listening", "Reading", "Grammar", "Vocabulary"
}
