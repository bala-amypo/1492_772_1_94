package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.ApiResponse;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // 🔹 REGISTER
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody AuthRequest request) {

        // Added default role "USER" for registration
        User user = userService.registerUser(
                request.getEmail(),
                request.getPassword(),
                "USER"
        );

        // ApiResponse expects String success value
        return new ResponseEntity<>(
                new ApiResponse("User registered successfully", "true", user),
                HttpStatus.CREATED
        );
    }

    // 🔹 LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {

        User user = userService.getByEmail(request.getEmail());

        if (user == null || !user.getPassword().equals(request.getPassword())) {
            return new ResponseEntity<>(
                    new ApiResponse("Invalid email or password", "false", null),
                    HttpStatus.UNAUTHORIZED
            );
        }

        // Convert Long id to String
        AuthResponse response = new AuthResponse(
                String.valueOf(user.getId()),
                user.getEmail(),
                user.getRole(),
                "dummy-jwt-token" // placeholder for JWT token
        );

        return ResponseEntity.ok(response);
    }
}
