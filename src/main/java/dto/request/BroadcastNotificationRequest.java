package dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BroadcastNotificationRequest {
    private String title;
    private String message;
    private String type; // "info", "success", "warning"
}
