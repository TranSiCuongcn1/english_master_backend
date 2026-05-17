package controller;

import dto.response.NotificationResponse;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import service.NotificationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAll(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(notificationService.getAll(user.getId()));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markRead(
            @AuthenticationPrincipal User user,
            @PathVariable String id
    ) {
        notificationService.markRead(user.getId(), UUID.fromString(id));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/read-all")
    public ResponseEntity<Void> markAllRead(
            @AuthenticationPrincipal User user
    ) {
        notificationService.markAllRead(user.getId());
        return ResponseEntity.ok().build();
    }
}
