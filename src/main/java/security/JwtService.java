package security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    //trich xuat Claim "Subject" (Email,Username) tu payload cua jwt
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    //generic method su dung higher-order function de trich xuat 1 claim cu the bat ki tu tap hop bo claim cua token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //factory method overload de tao token khong chua extra claims
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    //build va ma hoa 1 fwt hoan chinh
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts
                //tao doi tuong JwtBuilder trong de chuan bi nhan du lieu
                .builder()
                //do danh sach thong tin phu tro (extra claims) vao overload cua token
                .setClaims(extraClaims)
                //thiet lap trường subject, de biet token cua ai
                .setSubject(userDetails.getUsername())
                //thiet lap truong iat ghi lai thoi gian token nay duoc tao ra
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //thiet lap truong exp ghi lai thoi gian token nay het han
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                //dong dau chu ki dien tu (signature)
                //dung thuat toan bam HMAC SHA-256
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                //Bước đóng gói cuối cùng. Lệnh này sẽ gom Header (chứa loại token, thuật toán), Payload (chứa Claims, Subject, Thời gian) và Signature (Chữ ký điện tử) lại. Sau đó, nó chuyển đổi tất cả sang dạng chuỗi an toàn cho URL (Base64Url encoding) và ghép lại với nhau bằng dấu chấm
                .compact();
    }
    //xac thuc tinh toan ven va nghiep vu cua token
    public boolean isTokenValid(String token, UserDetails userDetails){
        //call function tach phan payload cua token de lay truong object
        final String username = extractUsername(token);
        //check username cua token and database xem co khop khong, check token time
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    //check logic expiration time
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    //trich xuat metadata "Expiration" tu bo Claims
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    //
    private Claims extractAllClaims(String token) {
        return Jwts
                //tao co may quet Parser chuyen dung de doc jwt
                .parserBuilder()
                //cung cap Key
                .setSigningKey(getSignInKey())
                .build()
                //Đưa chuỗi JWT (cái thẻ từ) vào máy quét
                //1. Token có đúng định dạng gồm 3 phần (Header.Payload.Signature) không?
                //2. Chữ ký (Signature) có hợp lệ không?
                //3. Token có hết hạn không?
                .parseClaimsJws(token)
                //if token dung thi lay payload va tra ve Claims.
                .getBody();
    }

    private Key getSignInKey() {
        //Dòng lệnh này làm nhiệm vụ "dịch ngược" (decode) chuỗi văn bản
        //Base64 đó trở lại hình hài gốc nguyên thủy của nó: một mảng các byte dữ liệu (byte[]).
        byte [] keyBytes = Decoders.BASE64.decode(secretKey);
        //chuyen mang byte thanh key
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
