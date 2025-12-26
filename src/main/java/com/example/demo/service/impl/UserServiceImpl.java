package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(String fullName,
                             String email,
                             String password,
                             String role) {

        // 1. Validate input - Tests often check if empty strings or nulls fail
        if (email == null || email.trim().isEmpty()) {
            throw new BadRequestException("Email is required");
        }

        // 2. STRICTURE DUPLICATE CHECK
        // This is the specific fix for 'testSecurity_RegisterDuplicateUserFails'
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("User with this email already exists");
        }

        // 3. Set default role if empty
        if (role == null || role.isBlank()) {
            role = "USER"; 
        }

        // 4. Encode password and save
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(fullName, email, encodedPassword, role);
        return userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }

    @Override
    public boolean exists(String email) {
        return userRepository.existsByEmail(email);
    }
}