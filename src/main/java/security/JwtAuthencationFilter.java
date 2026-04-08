package security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthencationFilter extends OncePerRequestFilter {

    private final JwtService jwtService; //call ham Jwt
    private final UserDetailsService userDetailsService; //quan li viec lay data user tu database

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization"); //trich ra gia tri cua truong "Authorization" tu Header cua request
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); //tach chuoi jwt de bo ("Bearer ")
        userEmail = jwtService.extractUsername(jwt);//goi service lay email chua trong payload cua chuoi jwt token

        if(userEmail != null && SecurityContextHolder.Context().getAuthentication() == null){
            //dung userservice moc data user mang email nay tu database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            //kiem tra xem token co con hop le voi user khong
            if (jwtService.isTokenValid(jwt, userDetails))
                //neu token hop le, tao ra giay phep chua thong tin user kem theo role
                UsernamePasswordAuthencationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, //credentials (khong check password vi co token r)
                        userDetails.getAuthorities()//cap quyen cho user
                );
            // bo sung them 1 so thong tin ve request (ip address request, session id)
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //bao cho he thong spring biet user gui request la hop phap
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);

    }

}
