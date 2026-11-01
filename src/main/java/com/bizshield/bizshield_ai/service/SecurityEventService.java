package com.bizshield.bizshield_ai.service;

import com.bizshield.bizshield_ai.model.SecurityEvent;
import com.bizshield.bizshield_ai.repository.SecurityEventRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SecurityEventService {

    private final SecurityEventRepository securityEventRepository;
    private final EmailNotificationService emailNotificationService;


    public SecurityEventService(
            SecurityEventRepository securityEventRepository,
            EmailNotificationService emailNotificationService) {

        this.securityEventRepository = securityEventRepository;
        this.emailNotificationService = emailNotificationService;
    }


    @Transactional
    public SecurityEvent recordEvent(
            String eventType,
            String description) {

        // Create security event
        SecurityEvent event = new SecurityEvent(
                eventType,
                description);

        // Save event in database
        SecurityEvent savedEvent =
                securityEventRepository.save(event);


        // Send email notification
        try {

            emailNotificationService.sendSecurityNotification(
                    "BizShield AI Security Alert: " + eventType,

                    "A security event occurred in your BizShield AI account.\n\n"
                            + "Event Type: " + eventType + "\n"
                            + "Description: " + description + "\n"
                            + "Time: " + savedEvent.getEventTime() + "\n\n"
                            + "Please review your BizShield AI dashboard if this activity was unexpected."
            );

        } catch (Exception exception) {

            // Email failure should not stop the application
            System.err.println(
                    "Failed to send security notification email: "
                            + exception.getMessage()
            );
        }


        return savedEvent;
    }


    @Transactional(readOnly = true)
    public List<SecurityEvent> getRecentEvents() {

        return securityEventRepository.findAll(
                org.springframework.data.domain.Sort
                        .by(
                                org.springframework.data.domain.Sort.Direction.DESC,
                                "eventTime"
                        )
        );
    }
}