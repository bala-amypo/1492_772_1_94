package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestParam @NotBlank String fullName,
            @RequestParam @Email String email,
            @RequestParam @NotBlank String password) {

        User user = userService.registerUser(
                fullName, email, password);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // ✅ LOGIN (simple validation, NO JWT)
    @PostMapping("/login")
    public ResponseEntity<User> login(
            @RequestParam @Email String email,
            @RequestParam @NotBlank String password) {

        User user = userService.getByEmail(email);

        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        return ResponseEntity.ok(user);
    }
}
