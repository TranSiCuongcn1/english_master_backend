package config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import repository.UserRepository;

// Annotation @Configuration đánh dấu class này chứa các thông số khởi tạo ApplicationContext.
// Các method mang @Bean sẽ được Spring container trigger để tạo object (IoC) trong quá trình boot ứng dụng.
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    // Injection interface Repository dùng để truy xuất CSDL tầng Data Access Layer (DAL).
    private final UserRepository userRepository;

    // Đăng ký Bean khai báo Implementation cho UserDetailsService của Spring Security.
    // Core interface quy định cơ chế load User thông qua Username.
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                // Bắn ngoại lệ authentication logic nếu kết quả trả về từ DB rỗng (Optional empty).
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }

    // Khởi tạo AuthenticationProvider - Component nòng cốt chịu trách nhiệm thực thi Authentication operations.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Bind UserDetailsService vào Provider để nó biết cơ chế trích xuất Entity Model từ DB.
        authProvider.setUserDetailsService(userDetailsService());
        // Bind thuật toán Hashing để Provider tự động decode/so khớp mật khẩu plain text với chuỗi hash mã hóa từ DB.
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Đăng ký Bean xuất proxy AuthenticationManager (Người phân phối authentication process) ra IoC container.
    // AuthManager là thành phần bạn sẽ autowire vào AuthenticationService để thực thi việc xác thực dựa trên (Email/Password).
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Khởi tạo thuật toán Hash mật khẩu sử dụng tiêu chuẩn Bcrypt mật mã hóa 1 chiều (Salting + Hashing).
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Trả về instance của cấu hình Bcrypt dùng chung cho toàn bộ Application (mặc định độ rải salt là độ phức tạp 10 block).
        return new BCryptPasswordEncoder();
    }
}
