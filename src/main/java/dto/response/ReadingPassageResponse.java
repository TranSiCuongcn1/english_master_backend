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
public class ReadingPassageResponse {
    private String id;
    private String title;
    private String text;
    private String difficulty;
    private List<QuestionResponse> questions;
    private Integer readingTime;
}
