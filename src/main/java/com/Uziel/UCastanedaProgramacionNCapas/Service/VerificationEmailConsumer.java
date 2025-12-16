package com.Uziel.UCastanedaProgramacionNCapas.Service;

import com.Uziel.UCastanedaProgramacionNCapas.DTO.VerificationEmailMessage;
import jakarta.mail.MessagingException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class VerificationEmailConsumer {

    private final EmailService emailService;

    public VerificationEmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @JmsListener(destination = "queue.email.verification")
    public void receiveVerificationEmail(VerificationEmailMessage message){

        try {
            emailService.sendVerificationEmail(
                    message.getEmail(),
                    message.getNombre(),
                    message.getToken());

        } catch (MessagingException ex) {
            System.err.println("Error enviando correo de verificación" + ex.getMessage());
        }
    }
}
