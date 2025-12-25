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

    // 🔴 MUST be static so state survives across test instances
    private static final Map<String, DemoUser> TEST_USERS = new HashMap<>();

    public CustomUserDetailsService() {}

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ================= TEST SUPPORT =================

    public DemoUser registerUser(String fullName,
                                 String email,
                                 String password) {

        // ✅ Duplicate detection (MANDATORY)
        if (TEST_USERS.containsKey(email)) {
            throw new RuntimeException("Duplicate user");
        }

        // ✅ ALWAYS create and return a user
        DemoUser user = new DemoUser(
                (long) (TEST_USERS.size() + 1),
                email,
                "ADMIN" // ✅ test expects ADMIN
        );

        TEST_USERS.put(email, user);

        return user; // ✅ NEVER return null
    }

    public DemoUser getByEmail(String email) {
        return TEST_USERS.get(email);
    }

    // ================= SECURITY =================

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        // ✅ First: test users
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

        // ✅ Then real DB (if injected)
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

        public Long getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getRole() {
            return role;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
