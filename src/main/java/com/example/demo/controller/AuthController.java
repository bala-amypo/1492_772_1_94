package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(
            @RequestBody User user) {

        User savedUser = userService.registerUser(
                user.getFullName(),
                user.getEmail(),
                user.getPassword(),
                "USER"   // ✅ REQUIRED 4th ARGUMENT
        );

        return ResponseEntity.ok(
                new ApiResponse<>(true,
                        "User registered successfully",
                        savedUser)
        );
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userService.getByEmail(request.getEmail());

        String token = jwtTokenProvider.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );

        return ResponseEntity.ok(
                new AuthResponse(
                        token,
                        user.getId(),
                        user.getEmail(),
                        user.getRole()
                )
        );
    }
}
