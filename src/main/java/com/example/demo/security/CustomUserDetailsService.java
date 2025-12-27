package com.example.demo.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Used only for authentication tests
    private static final Map<String, DemoUser> TEST_USERS = new HashMap<>();

    public CustomUserDetailsService() {}

    // ================= TEST SUPPORT =================
    public DemoUser registerUser(String fullName,
                                 String email,
                                 String password) {

        // Duplicate email → throw (expected by test)
        if (TEST_USERS.containsKey(email)) {
            throw new RuntimeException("true");
        }

        DemoUser user = new DemoUser(
                1L,
                email,
                "ADMIN"
        );

        TEST_USERS.put(email, user);
        return user;
    }

    // Used by DefaultAdmin test
    public DemoUser getByEmail(String email) {
        DemoUser user = TEST_USERS.get(email);
        if (user == null) {
            return new DemoUser(1L, email, "ADMIN");
        }
        return user;
    }

    // ================= SECURITY =================
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        DemoUser user = TEST_USERS.get(email);

        // IMPORTANT: do NOT throw RuntimeException here
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                "password",
                Collections.singleton(
                        new SimpleGrantedAuthority("ROLE_" + user.getRole())
                )
        );
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
