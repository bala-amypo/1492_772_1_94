package com.example.demo.controller;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserLoginDTO;
import com.example.demo.dto.UserRegisterDTO;
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

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserRegisterDTO dto) {
        UserDTO created = userService.registerUser(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody UserLoginDTO dto) {
        return ResponseEntity.ok(userService.login(dto));
    }
}
