package service;

import dto.request.BroadcastNotificationRequest;
import dto.response.NotificationResponse;
import entity.NotifType;
import entity.Notification;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.NotificationRepository;
import repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<NotificationResponse> getAll(UUID userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void markRead(UUID userId, UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        if (!notification.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    @Transactional
    public void markAllRead(UUID userId) {
        notificationRepository.markAllReadByUserId(userId);
    }

    @Transactional
    public void broadcast(BroadcastNotificationRequest request) {
        NotifType type = NotifType.valueOf(request.getType().toLowerCase());
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            Notification notification = Notification.builder()
                    .user(user)
                    .title(request.getTitle())
                    .message(request.getMessage())
                    .type(type)
                    .isRead(false)
                    .build();
            notificationRepository.save(notification);
        }
    }

    private NotificationResponse toResponse(Notification n) {
        return NotificationResponse.builder()
                .id(n.getId().toString())
                .title(n.getTitle())
                .message(n.getMessage())
                .type(n.getType() != null ? n.getType().name().toLowerCase() : "info")
                .date(n.getCreatedAt() != null ? n.getCreatedAt().toString() : null)
                .isRead(n.getIsRead())
                .build();
    }
}
