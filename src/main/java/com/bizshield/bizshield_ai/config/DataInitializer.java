package com.bizshield.bizshield_ai.config;

import com.bizshield.bizshield_ai.repository.UserRepository;
import com.bizshield.bizshield_ai.service.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createViewerUser(
            UserRepository userRepository,
            UserService userService) {

        return args -> {

            if (!userRepository.existsByUsername("vieweruser")) {

                userService.createUser(
                        "vieweruser",
                        "ViewerPass123!",
                        "VIEWER"
                );

                System.out.println(
                        "VIEWER USER CREATED SUCCESSFULLY"
                );
            }
        };
    }
}