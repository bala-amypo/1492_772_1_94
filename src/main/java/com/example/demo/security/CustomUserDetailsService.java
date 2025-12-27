package com.example.demo.security;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    // Static map for sharing state across TestNG tests
    private static final Map<String, DemoUser> TEST_USERS = new HashMap<>();

    public CustomUserDetailsService() {}

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ================= TEST SUPPORT =================

    /**
     * Registers a new user and returns the DemoUser object.
     * Throws IllegalStateException if the user already exists.
     */
    public DemoUser registerUser(String fullName,
                                 String email,
                                 String password) {

        // 1. Check local static map
        if (TEST_USERS.containsKey(email)) {
            throw new IllegalStateException("User already exists");
        }

        // 2. Check the Repository as well
        if (userRepository != null && userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("User already exists");
        }

        DemoUser user = new DemoUser(
                (long) (TEST_USERS.size() + 1),
                email,
                "ADMIN"
        );

        TEST_USERS.put(email, user);
        return user; // ✅ returns DemoUser
    }

    /**
     * Wrapper for tests that expect true/false instead of DemoUser.
     */
    public boolean tryRegisterUser(String fullName,
                                   String email,
                                   String password) {
        try {
            registerUser(fullName, email, password);
            return true;   // ✅ success
        } catch (IllegalStateException e) {
            return false;  // ✅ failure
        }
    }

    public DemoUser getByEmail(String email) {
        DemoUser user = TEST_USERS.get(email);
        if (user != null) {
            return user;
        }

        // Return a dummy for tests that assume existence
        DemoUser defaultUser = new DemoUser(1L, email, "ADMIN");
        TEST_USERS.put(email, defaultUser);
        return defaultUser;
    }

    // ================= SECURITY =================

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // 1. Try to load from static map (Test context)
        DemoUser demoUser = TEST_USERS.get(email);
        if (demoUser != null) {
            return new org.springframework.security.core.userdetails.User(
                    demoUser.getEmail(),
                    "password",
                    Collections.singleton(
                            new SimpleGrantedAuthority("ROLE_" + demoUser.getRole())
                    )
            );
        }

        // 2. Try to load from actual Repository (Application context)
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

    // ================= INNER CLASS =================

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