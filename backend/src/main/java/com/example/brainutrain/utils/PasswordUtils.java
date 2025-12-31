package com.example.brainutrain.utils;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Utility class for password encoding and matching.
 * Wraps PasswordEncoder to avoid circular dependency issues.
 */
@Component
@AllArgsConstructor
public class PasswordUtils {

    private final PasswordEncoder passwordEncoder;

    /**
     * Encodes a raw password using BCrypt
     *
     * @param rawPassword the raw password to encode
     * @return the encoded password
     */
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Checks if a raw password matches an encoded password
     *
     * @param rawPassword the raw password to check
     * @param encodedPassword the encoded password to match against
     * @return true if passwords match, false otherwise
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
