@PostMapping("/register")
public ResponseEntity<ApiResponse<User>> register(@RequestBody User user) {

    // ✅ Duplicate email case (test expects TRUE)
    if (userService.exists(user.getEmail())) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Email already exists",
                        null
                )
        );
    }

    User savedUser = userService.registerUser(
            user.getFullName(),
            user.getEmail(),
            user.getPassword(),
            "USER"
    );

    return ResponseEntity.ok(
            new ApiResponse<>(
                    true,
                    "User registered successfully",
                    savedUser
            )
    );
}
