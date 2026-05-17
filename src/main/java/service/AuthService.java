package service;

import dto.auth.AuthResponse;
import dto.auth.LoginRequest;
import dto.auth.RegisterRequest;
import entity.User;
import entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.user)
                .dailyGoalTarget(5)
                .build();
        
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        
        return buildAuthResponse(user, jwtToken);
    }

    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        var jwtToken = jwtService.generateToken(user);
        
        return buildAuthResponse(user, jwtToken);
    }

    public AuthResponse.UserInfo getMe(User user) {
        return AuthResponse.UserInfo.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name().toLowerCase()) // "USER" → "user", "ADMIN" → "admin"
                .avatarUrl(user.getAvatarUrl())
                .build();
    }

    private AuthResponse buildAuthResponse(User user, String token) {
        return AuthResponse.builder()
                .token(token)
                .user(AuthResponse.UserInfo.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole().name().toLowerCase()) // "USER" → "user", "ADMIN" → "admin"
                        .avatarUrl(user.getAvatarUrl())
                        .build())
                .build();
    }
}
