package controller;

import dto.request.ChangePasswordRequest;
import dto.request.UpdateProfileRequest;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/me")
    public ResponseEntity<Map<String, String>> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody UpdateProfileRequest request
    ) {
        User updated = userService.updateProfile(user.getId(), request);
        return ResponseEntity.ok(Map.of(
                "name", updated.getName(),
                "email", updated.getEmail()
        ));
    }

    @PutMapping("/me/password")
    public ResponseEntity<Map<String, String>> changePassword(
            @AuthenticationPrincipal User user,
            @RequestBody ChangePasswordRequest request
    ) {
        userService.changePassword(user.getId(), request);
        return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteAccount(
            @AuthenticationPrincipal User user
    ) {
        userService.deleteAccount(user.getId());
        return ResponseEntity.ok().build();
    }
}
