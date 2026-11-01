package com.bizshield.bizshield_ai.security;

import com.bizshield.bizshield_ai.service.SecurityEventService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final SecurityEventService securityEventService;

    public CustomLogoutSuccessHandler(
            SecurityEventService securityEventService) {

        this.securityEventService = securityEventService;
    }

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        if (authentication != null) {

            securityEventService.recordEvent(
                    "LOGOUT_SUCCESS",
                    "Successful logout by user: "
                            + authentication.getName()
            );
        }

        // Redirect to the professional logout success page
        response.sendRedirect("/logout-success.html");
    }
}