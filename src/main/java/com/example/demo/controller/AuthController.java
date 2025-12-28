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
@CrossOrigin(origins = "*") // IMPORTANT for frontend calls
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ✅ REGISTER (NO TOKEN REQUIRED)
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @RequestBody AuthRequest request) {

        User user = userService.register(request);

        ApiResponse response = new ApiResponse(
                true,
                "User registered successfully",
                user
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ✅ LOGIN (JWT TOKEN)
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
            @RequestBody AuthRequest request) {

        ApiResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
