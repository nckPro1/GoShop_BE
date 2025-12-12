package org.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey; // CHÚ Ý: Sử dụng javax.crypto.SecretKey
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    // ====================================================================
    // 1. CHỨC NĂNG TẠO TOKEN
    // ====================================================================

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                // ĐÃ SỬA LỖI: Cú pháp signWith mới (Key, MacAlgorithm)
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    // ====================================================================
    // 2. CHỨC NĂNG XÁC THỰC/GIẢI MÃ TOKEN
    // ====================================================================

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // ĐÃ SỬA LỖI: Cú pháp giải mã mới (verifyWith)
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                // Sử dụng verifyWith() với SecretKey để xác thực
                .verifyWith(getSignInKey())
                .build()
                // Sử dụng parseSignedClaims() và lấy Payload (Claims)
                .parseSignedClaims(token)
                .getPayload();
    }

    // ====================================================================
    // 3. HÀM TẠO SECRET KEY (ĐÃ ĐỔI KIỂU TRẢ VỀ)
    // ====================================================================

    // ĐÃ SỬA LỖI: Đổi kiểu trả về từ Key sang SecretKey
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}