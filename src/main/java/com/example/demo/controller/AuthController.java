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

    // 🔹 USER REGISTRATION
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody AuthRequest request) {

        User user = userService.registerUser(
                request.getFullName(),
                request.getEmail(),
                request.getPassword()
        );

        return new ResponseEntity<>(
                new ApiResponse("User registered successfully!", true),
                HttpStatus.CREATED
        );
    }

    // 🔹 USER LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {

        User user = userService.getByEmail(request.getEmail());

        if (user == null || !user.getPassword().equals(request.getPassword())) {
            return new ResponseEntity<>(
                    new ApiResponse("Invalid email or password", false),
                    HttpStatus.UNAUTHORIZED
            );
        }

        AuthResponse response = new AuthResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail()
        );

        return ResponseEntity.ok(response);
    }
}
