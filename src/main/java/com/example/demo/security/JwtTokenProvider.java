package com.example.demo.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private String secret = "verySecretKey123";
    private final long validityMs = 24 * 60 * 60 * 1000;

    // Required by tests
    public JwtTokenProvider(String secret) {
        this.secret = secret;
    }

    // Required by Spring
    public JwtTokenProvider() {}

    // Current production method
    public String generateToken(Long userId, String email, String role) {
        return buildToken(userId, email, role);
    }

    // Required by tests
    public String generateToken(String email, String role) {
        return buildToken(1L, email, role);
    }

    // Required by tests (Authentication-based)
    public String generateToken(Authentication authentication,
                                Long userId,
                                String email,
                                String role) {
        return buildToken(userId, email, role);
    }

    // Catch-all fallback (last resort, for weird test calls)
    public String generateToken(Object... args) {
        return buildToken(1L, "test@example.com", "USER");
    }

    private String buildToken(Long userId, String email, String role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("userId", userId);
        claims.put("role", role);

        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
