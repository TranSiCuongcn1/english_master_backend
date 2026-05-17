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
public class VocabularyTopicResponse {
    private String id;
    private String title;
    private String description;
    private List<WordResponse> words;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WordResponse {
        private String word;
        private String type;
        private String meaning;
        private String example;
    }
}
