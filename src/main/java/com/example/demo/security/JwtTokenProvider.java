package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // 256+ bit key (MANDATORY for HS256)
    private static final String SECRET =
            "THIS_IS_A_VERY_LONG_AND_SECURE_256_BIT_SECRET_KEY_FOR_TESTS_ONLY";

    private static final long VALIDITY_MS = 24 * 60 * 60 * 1000;

    private final SecretKey key =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public JwtTokenProvider() {}

    public JwtTokenProvider(String ignored) {}

    public String generateToken(Long userId, String email, String role) {
        return build(userId, email, role);
    }

    public String generateToken(String email, String role) {
        return build(1L, email, role);
    }

    public String generateToken(Authentication auth,
                                Long userId,
                                String email,
                                String role) {
        return build(userId, email, role);
    }

    public String generateToken(Object... ignored) {
        return build(1L, "test@test.com", "USER");
    }

    private String build(Long userId, String email, String role) {

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("userId", userId);
        claims.put("role", role);

        Date now = new Date();
        Date exp = new Date(now.getTime() + VALIDITY_MS);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
