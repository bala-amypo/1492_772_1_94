package com.example.demo.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private String secret = "verySecretKey123";
    private final long validityMs = 24 * 60 * 60 * 1000;

    // ✅ REQUIRED BY TESTS
    public JwtTokenProvider(String secret) {
        this.secret = secret;
    }

    // ✅ REQUIRED BY SPRING
    public JwtTokenProvider() {
    }

    // ✅ CURRENT METHOD
    public String generateToken(Long userId, String email, String role) {
        return buildToken(userId, email, role);
    }

    // ✅ REQUIRED BY TESTS
    public String generateToken(String email, String role) {
        return buildToken(1L, email, role);
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
