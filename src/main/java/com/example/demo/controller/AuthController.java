package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.AuthRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ✅ REGISTER (NO TOKEN REQUIRED)
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody AuthRequest request) {

        // fullName & role are not in AuthRequest → handled here
        String fullName = "User";        // default value
        String role = "USER";            // default role

        User user = userService.registerUser(
                fullName,
                request.getEmail(),
                request.getPassword(),
                role
        );

        ApiResponse response = new ApiResponse(
                true,
                "User registered successfully",
                user
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 🚧 LOGIN (TEMP PLACEHOLDER – JWT comes later)
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody AuthRequest request) {

        // Just verify user exists (no JWT yet)
        User user = userService.getByEmail(request.getEmail());

        ApiResponse response = new ApiResponse(
                true,
                "Login successful",
                user
        );

        return ResponseEntity.ok(response);
    }
}
