package dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaderboardEntryResponse {
    private String userEmail;
    private String userName;
    private Long totalTests;
    private Double avgAccuracy;
    private Long totalScore;
}
