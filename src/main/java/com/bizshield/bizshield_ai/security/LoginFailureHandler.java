
package com.bizshield.bizshield_ai.security;

import com.bizshield.bizshield_ai.service.SecurityEventService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginFailureHandler
        extends SimpleUrlAuthenticationFailureHandler {

    private final SecurityEventService securityEventService;

    public LoginFailureHandler(
            SecurityEventService securityEventService) {
        this.securityEventService = securityEventService;
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {

        String username = request.getParameter("username");

        if (username == null || username.isBlank()) {
            username = "unknown";
        }

        securityEventService.recordEvent(
                "LOGIN_FAILURE",
                "Failed login attempt for username: " + username);

        super.onAuthenticationFailure(request, response, exception);
    }
}