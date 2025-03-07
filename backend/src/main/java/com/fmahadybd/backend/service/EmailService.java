package com.fmahadybd.backend.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender JavaMailSender) {
        this.javaMailSender = JavaMailSender;
    }

    public void sendSimpleEmail(String to, String subject, String body) throws MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body,true);
        javaMailSender.send(message);

        
    }


}
