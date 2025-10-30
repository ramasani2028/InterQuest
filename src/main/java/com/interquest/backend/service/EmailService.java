package com.interquest.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendNotificationEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("sr24csb0a64@student.nitw.ac.in");

        try {
            mailSender.send(message);
            System.out.println(STR."Notification email sent successfully to: \{toEmail}");
        } catch (Exception e) {
            System.err.println(STR."Error sending notification email to \{toEmail}: \{e.getMessage()}");
        }
    }
}