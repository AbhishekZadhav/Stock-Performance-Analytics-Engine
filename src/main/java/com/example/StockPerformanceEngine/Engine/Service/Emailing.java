package com.example.StockPerformanceEngine.Engine.Service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.File;

@Service
public class Emailing {

    private final JavaMailSender mailSender;

    public Emailing(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMailWithAttachment(
            String to,
            String subject,
            String body,
            String filePath
    ) throws MessagingException {
        System.out.println("OutPut FilePath:: "+ filePath);
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist: " + filePath);
        }

        if (!file.isFile()) {
            throw new IllegalArgumentException("Path is not a file: " + filePath);
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        helper.addAttachment(file.getName(), file);

        mailSender.send(message);
    }
}
