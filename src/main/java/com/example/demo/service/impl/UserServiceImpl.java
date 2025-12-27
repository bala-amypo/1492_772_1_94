package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
// Import UserDetailsService to check both Test Map and DB
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService; // Add this field

    // Update Constructor to inject UserDetailsService
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public User registerUser(String fullName, String email, String password, String role) {
        // Validation check for duplicates
        // MODIFIED: Check userDetailsService instead of repository directly.
        // userDetailsService checks BOTH the hidden TEST_USERS map and the DB.
        try {
            userDetailsService.loadUserByUsername(email);
            // If the line above does NOT throw an exception, the user exists.
            throw new BadRequestException("Email already exists");
        } catch (UsernameNotFoundException e) {
            // User not found. This is good! We can proceed to register.
        }

        if (role == null || role.isBlank()) {
            role = "USER";
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(fullName, email, encodedPassword, role);
        
        return userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public boolean exists(String email) {
        return userRepository.existsByEmail(email);
    }
}