
package com.bizshield.bizshield_ai.controller;

import com.bizshield.bizshield_ai.model.User;
import com.bizshield.bizshield_ai.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(
            @RequestBody CreateUserRequest request) {

        User user = userService.createUser(
                request.username(),
                request.password(),
                request.role()
        );

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }

    public record CreateUserRequest(
            String username,
            String password,
            String role
    ) {}
}