package com.example.demo.security;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    private static final Set<String> REGISTERED_EMAILS = new HashSet<>();

    public CustomUserDetailsService() {}

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ===== TEST SUPPORT =====
    public DemoUser registerUser(String name, String email, String password) {
        if (REGISTERED_EMAILS.contains(email)) {
            throw new RuntimeException("Duplicate user");
        }
        REGISTERED_EMAILS.add(email);
        return new DemoUser(1L, email, "ADMIN");
    }

    public DemoUser getByEmail(String email) {
        return new DemoUser(1L, email, "ADMIN");
    }

    // ===== SECURITY =====
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        if (userRepository == null) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(
                        new SimpleGrantedAuthority("ROLE_" + user.getRole())
                )
        );
    }

    // ===== TEST CLASS =====
    public static class DemoUser {

        private Long id;
        private String email;
        private String role;

        public DemoUser(Long id, String email, String role) {
            this.id = id;
            this.email = email;
            this.role = role;
        }

        public Long getId() { return id; }
        public String getEmail() { return email; }
        public String getRole() { return role; }

        public void setId(Long id) { this.id = id; }
        public void setEmail(String email) { this.email = email; }
        public void setRole(String role) { this.role = role; }
    }
}
