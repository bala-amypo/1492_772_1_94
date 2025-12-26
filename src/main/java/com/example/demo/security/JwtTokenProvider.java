package com.example.demo.security;

import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY =
            "MyVeryStrongJwtSecretKeyThatIsAtLeast256BitsLong!";

    private final long EXPIRATION = 24 * 60 * 60 * 1000;

    public JwtTokenProvider() {
    }

    public JwtTokenProvider(Object... args) {
    }

    public String generateToken(Long id, String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("id", id)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(
                        Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public String generateToken(
            UsernamePasswordAuthenticationToken authentication,
            Long id,
            String email,
            String role) {

        return generateToken(id, email, role);
    }

    public String generateToken(
            UsernamePasswordAuthenticationToken authentication,
            long id,
            String email,
            String role) {

        return generateToken(Long.valueOf(id), email, role);
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
