package com.bizshield.bizshield_ai.config;

import com.bizshield.bizshield_ai.repository.UserRepository;
import com.bizshield.bizshield_ai.service.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createDefaultUsers(
            UserRepository userRepository,
            UserService userService) {

        return args -> {

            // ADMIN USER
            if (!userRepository.existsByUsername("admin")) {

                userService.createUser(
                        "admin",
                        "AdminPass123!",
                        "ADMIN"
                );

                System.out.println("ADMIN USER CREATED SUCCESSFULLY");
            }


            // BUSINESS USER
            if (!userRepository.existsByUsername("businessuser")) {

                userService.createUser(
                        "businessuser",
                        "BusinessPass123!",
                        "BUSINESS_USER"
                );

                System.out.println("BUSINESS USER CREATED SUCCESSFULLY");
            }


            // VIEWER USER
            if (!userRepository.existsByUsername("vieweruser")) {

                userService.createUser(
                        "vieweruser",
                        "ViewerPass123!",
                        "VIEWER"
                );

                System.out.println("VIEWER USER CREATED SUCCESSFULLY");
            }
        };
    }
}