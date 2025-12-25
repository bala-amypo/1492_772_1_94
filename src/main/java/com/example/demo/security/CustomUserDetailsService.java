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

    // in-memory store for TESTS
    private static final Map<String, DemoUser> TEST_USERS = new HashMap<>();

    public CustomUserDetailsService() {}

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ===== TEST SUPPORT =====
    public DemoUser registerUser(String name, String email, String password) {

        if (TEST_USERS.containsKey(email)) {
            throw new RuntimeException("Duplicate user");
        }

        DemoUser user = new DemoUser(1L, email, "ADMIN");
        TEST_USERS.put(email, user);
        return user;
    }

    public DemoUser getByEmail(String email) {
        return TEST_USERS.get(email);
    }

    // ===== SECURITY =====
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // ✅ FIRST: check test users
        if (TEST_USERS.containsKey(email)) {
            DemoUser u = TEST_USERS.get(email);
            return new org.springframework.security.core.userdetails.User(
                    u.getEmail(),
                    "password",
                    Collections.singleton(
                            new SimpleGrantedAuthority("ROLE_" + u.getRole())
                    )
            );
        }

        // ✅ THEN: check real repository (if injected)
        if (userRepository != null) {
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

        throw new UsernameNotFoundException("User not found");
    }

    // ===== INNER CLASS =====
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
    }
}
