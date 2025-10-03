package com.scm.services;

import jakarta.mail.MessagingException;

public interface EmailService {

    // Send a simple plain-text email
    void sendEmail(String to, String subject, String body);

    // Send an HTML email
    void sendEmailWithHtml(String to, String subject, String htmlBody) throws MessagingException;

    // Send an email with attachment
    void sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) throws MessagingException;
}
