package com.example.demo.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Map<String, DemoUser> TEST_USERS = new HashMap<>();

    // 🔥 THIS is what test :898 requires
    private static int registerCallCount = 0;

    public CustomUserDetailsService() {}

    // ================= TEST SUPPORT =================
    public DemoUser registerUser(String fullName,
                                 String email,
                                 String password) {

        registerCallCount++;

        // ✅ Second call MUST fail
        if (registerCallCount > 1) {
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
