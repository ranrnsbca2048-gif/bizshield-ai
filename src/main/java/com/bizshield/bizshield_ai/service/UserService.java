package com.bizshield.bizshield_ai.service;

import com.bizshield.bizshield_ai.model.User;
import com.bizshield.bizshield_ai.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(
            String username,
            String rawPassword,
            String role) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(
                    "Username is required.");
        }

        if (rawPassword == null || rawPassword.length() < 12) {
            throw new IllegalArgumentException(
                    "Password must contain at least 12 characters.");
        }

        if (!"ADMIN".equals(role)
        && !"BUSINESS_USER".equals(role)
        && !"VIEWER".equals(role)) {

    throw new IllegalArgumentException(
            "Invalid user role.");
}

        String normalizedUsername = username.trim();

        if (userRepository.existsByUsername(normalizedUsername)) {
            throw new IllegalArgumentException(
                    "Username already exists.");
        }

        User user = new User(
                normalizedUsername,
                passwordEncoder.encode(rawPassword),
                role,
                true);

        return userRepository.save(user);
    }
}