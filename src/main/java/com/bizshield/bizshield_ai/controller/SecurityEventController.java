
package com.bizshield.bizshield_ai.controller;

import com.bizshield.bizshield_ai.model.SecurityEvent;
import com.bizshield.bizshield_ai.service.SecurityEventService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/security-events")
public class SecurityEventController {

    private final SecurityEventService securityEventService;

    public SecurityEventController(
            SecurityEventService securityEventService) {
        this.securityEventService = securityEventService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<SecurityEvent> getSecurityEvents() {
        return securityEventService.getRecentEvents();
    }
}