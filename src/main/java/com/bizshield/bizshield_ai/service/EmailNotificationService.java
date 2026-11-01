package com.bizshield.bizshield_ai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSecurityNotification(
            String subject,
            String message) {

        SimpleMailMessage email = new SimpleMailMessage();

        email.setFrom(fromEmail);

        // Sends notification to your configured email
        email.setTo(fromEmail);

        email.setSubject(subject);

        email.setText(message);

        mailSender.send(email);
    }
}