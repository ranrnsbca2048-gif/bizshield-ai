
package com.bizshield.bizshield_ai.config;

import com.bizshield.bizshield_ai.repository.UserRepository;
import com.bizshield.bizshield_ai.service.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminBootstrapConfig {

    @Bean
    public CommandLineRunner createInitialAdmin(
            UserRepository userRepository,
            UserService userService) {

        return args -> {

            // Create an administrator only for an empty user table.
            if (userRepository.count() > 0) {
                return;
            }

            // Read credentials from environment variables.
            String username =
                    System.getenv("BIZSHIELD_ADMIN_USERNAME");

            String password =
                    System.getenv("BIZSHIELD_ADMIN_PASSWORD");

            // Never create an account with a default password.
            if (username == null || username.isBlank()
                    || password == null || password.isBlank()) {

                System.out.println(
                        "Initial admin setup skipped: "
                        + "bootstrap credentials are not configured.");

                return;
            }

            // UserService validates the account and hashes its password.
            userService.createUser(username, password, "ADMIN");

            System.out.println(
                    "Initial BizShield AI administrator account created.");
        };
    }
}