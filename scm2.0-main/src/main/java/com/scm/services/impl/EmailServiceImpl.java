package com.scm.services.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.scm.services.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender eMailSender;

    @Value("${spring.mail.properties.domain_name}")
    private String domainName;

    // ---------------------- SIMPLE EMAIL ----------------------
    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(domainName);

        // Uncomment this line to send real email
        // eMailSender.send(message);

        // Dummy log to show method works
        System.out.println("📩 Dummy EmailService: Email sending simulated!");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }

    // ---------------------- HTML EMAIL ----------------------
    @Override
    public void sendEmailWithHtml(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage mimeMessage = eMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true); // true = HTML content
        helper.setFrom(domainName);

        // Uncomment this line to send real email
        // eMailSender.send(mimeMessage);

        System.out.println("📩 Dummy EmailService: HTML Email sending simulated!");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("HTML Body: " + htmlBody);
    }

    // ---------------------- EMAIL WITH ATTACHMENT ----------------------
    @Override
    public void sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) throws MessagingException {
        MimeMessage mimeMessage = eMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        helper.setFrom(domainName);

        // Attach file
        FileSystemResource file = new FileSystemResource(new File(attachmentPath));
        helper.addAttachment(file.getFilename(), file);

        // Uncomment this line to send real email
        // eMailSender.send(mimeMessage);

        System.out.println("📩 Dummy EmailService: Email with attachment sending simulated!");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        System.out.println("Attachment: " + attachmentPath);
    }
}
