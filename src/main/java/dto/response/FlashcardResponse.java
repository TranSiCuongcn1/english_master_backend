package dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlashcardResponse {
    private String id;
    private String front;
    private String back;
    private String example;
    private String category;
    private Boolean isSystem;
    private String createdBy; // UUID string of user, null for system cards
}
