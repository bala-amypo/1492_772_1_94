package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.AuthRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // ✅ REGISTER (NO TOKEN REQUIRED)
    @PostMapping("/register")
    public ApiResponse register(@RequestBody AuthRequest request) {
        User user = userService.register(request);
        return new ApiResponse(true, "User registered successfully", user);
    }

    // ✅ LOGIN (JWT TOKEN)
    @PostMapping("/login")
    public ApiResponse login(@RequestBody AuthRequest request) {
        return userService.login(request);
    }
}
