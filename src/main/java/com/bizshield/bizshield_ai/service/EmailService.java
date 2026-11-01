package com.bizshield.bizshield_ai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSecurityAlert(
            String subject,
            String message) {

        SimpleMailMessage email = new SimpleMailMessage();

        email.setFrom(senderEmail);

        // Email address that receives BizShield alerts
        email.setTo(senderEmail);

        email.setSubject(subject);

        email.setText(message);

        mailSender.send(email);
    }
}