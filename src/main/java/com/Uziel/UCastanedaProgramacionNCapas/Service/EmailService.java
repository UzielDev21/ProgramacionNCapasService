package com.Uziel.UCastanedaProgramacionNCapas.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String toEmail, String nombre, String tokenEmail) throws MessagingException {
        
        String verificationLink = "http://localhost:8080/verify-account?token=" + tokenEmail;
        
        String htmlContent = """
                             hola, confirma tu correo
                             """.formatted(nombre, verificationLink);
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setTo(toEmail);
        helper.setSubject("verificación de cuenta");
        helper.setText(htmlContent, true);
        
        mailSender.send(message);
        
    }

}
