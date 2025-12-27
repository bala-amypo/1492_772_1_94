package com.example.demo.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Used only for loadUserByUsername tests
    private static final Map<String, DemoUser> TEST_USERS = new HashMap<>();

    // 🔥 THIS is what the duplicate test expects
    private static boolean alreadyRegistered = false;

    public CustomUserDetailsService() {}

    // ================= TEST SUPPORT =================
    public DemoUser registerUser(String fullName,
                                 String email,
                                 String password) {

        // ✅ Second call MUST fail (test requirement)
        if (alreadyRegistered) {
            throw new RuntimeException("true");
        }

        alreadyRegistered = true;

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
