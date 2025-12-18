package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public User register(@RequestBody Map<String, String> data) {
        return userService.registerUser(
                data.get("fullName"),
                data.get("email"),
                data.get("password")
        );
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> data) {
        User user = userService.getByEmail(data.get("email"));
        String token = jwtUtil.generateToken(user.getEmail());
        return Map.of("token", token);
    }
}
