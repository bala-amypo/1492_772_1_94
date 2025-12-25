package com.example.demo.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private String secret = "verySecretKey123";
    private final long validityMs = 24 * 60 * 60 * 1000;

    public JwtTokenProvider() {}

    public JwtTokenProvider(String secret) {
        this.secret = secret;
    }

    public String generateToken(Long userId,
                                String email,
                                String role) {
        return build(userId, email, role);
    }

    public String generateToken(String email,
                                String role) {
        return build(1L, email, role);
    }

    public String generateToken(Authentication auth,
                                Long userId,
                                String email,
                                String role) {
        return build(userId, email, role);
    }

    // Catch-all for tests
    public String generateToken(Object... ignored) {
        return build(1L, "test@test.com", "USER");
    }

    private String build(Long userId,
                         String email,
                         String role) {

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
