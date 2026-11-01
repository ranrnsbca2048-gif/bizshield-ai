package com.bizshield.bizshield_ai.security;

import com.bizshield.bizshield_ai.service.SecurityEventService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private final SecurityEventService securityEventService;

    public LoginSuccessHandler(
            SecurityEventService securityEventService) {

        this.securityEventService = securityEventService;

        setDefaultTargetUrl("/");
        setAlwaysUseDefaultTargetUrl(true);
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        securityEventService.recordEvent(
                "LOGIN_SUCCESS",
                "Successful login by user: "
                        + authentication.getName());

        super.onAuthenticationSuccess(
                request, response, authentication);
    }
}