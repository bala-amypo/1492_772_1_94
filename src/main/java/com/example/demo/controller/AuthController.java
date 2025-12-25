package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // REGISTER NEW USER
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestParam String fullName,
                                                @RequestParam String email,
                                                @RequestParam String password,
                                                @RequestParam String role) {
        try {
            User user = userService.registerUser(fullName, email, password, role);
            return ResponseEntity.ok(new ApiResponse(true, "User registered successfully", user));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, ex.getMessage()));
        }
    }

    // LOGIN USER
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        try {
            // Fetch user by email
            User user = userService.getByEmail(authRequest.getEmail());

            // Verify password
            if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid credentials"));
            }

            // Generate JWT token
            String token = jwtTokenProvider.generateToken(user.getEmail());

            // Return AuthResponse with Long userId
            AuthResponse authResponse = new AuthResponse(token, user.getId(), user.getEmail(), user.getRole());

            return ResponseEntity.ok(new ApiResponse(true, "Login successful", authResponse));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, ex.getMessage()));
        }
    }
}
