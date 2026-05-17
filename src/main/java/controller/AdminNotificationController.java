package controller;

import dto.request.BroadcastNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.NotificationService;

@RestController
@RequestMapping("/api/admin/notifications")
@RequiredArgsConstructor
public class AdminNotificationController {

    private final NotificationService notificationService;

    @PostMapping("/broadcast")
    public ResponseEntity<Void> broadcast(@RequestBody BroadcastNotificationRequest request) {
        notificationService.broadcast(request);
        return ResponseEntity.ok().build();
    }
}
