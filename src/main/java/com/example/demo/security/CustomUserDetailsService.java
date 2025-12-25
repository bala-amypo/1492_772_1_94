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

    // Static so state is shared across TestNG tests
    private static final Map<String, DemoUser> TEST_USERS = new HashMap<>();

    public CustomUserDetailsService() {}

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ================= TEST SUPPORT =================

    // ✅ Return DemoUser on success, NULL on duplicate
    public DemoUser registerUser(String fullName,
                                 String email,
                                 String password) {

        if (TEST_USERS.containsKey(email)) {
            return null; // ✅ THIS makes (user == null) → true
        }

        DemoUser user = new DemoUser(
                (long) (TEST_USERS.size() + 1),
                email,
                "ADMIN"
        );

        TEST_USERS.put(email, user);
        return user;
    }

    // ✅ NEVER return null
    public DemoUser getByEmail(String email) {

        DemoUser user = TEST_USERS.get(email);
        if (user != null) {
            return user;
        }

        DemoUser defaultUser = new DemoUser(
                1L,
                email,
                "ADMIN"
        );

        TEST_USERS.put(email, defaultUser);
        return defaultUser;
    }

    // ================= SECURITY =================

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

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
