package com.Uziel.UCastanedaProgramacionNCapas.Service;

import com.Uziel.UCastanedaProgramacionNCapas.DTO.PasswordResetEmailMessage;
import jakarta.mail.MessagingException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class PasswordEmailConsumer {

    private final EmailService emailService;

    public PasswordEmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @JmsListener(destination = "queue.email.passwordreset")
    public void receivePasswordEmail(PasswordResetEmailMessage message) {

        System.out.println("[JMS] mensaje reset-password recibido para: " + message.getEmail());

        try {
            emailService.sendVerificationEmail(
                    message.getEmail(),
                    message.getNombre(),
                    message.getToken());

            System.out.println("[MAIL] correo para cambio de contraseña enviado correctamente a: " + message.getEmail());

        } catch (MessagingException ex) {
            System.err.println("[MAIL ERROR] Error enviando correo a" + message.getEmail() + " -> " + ex.getMessage());
        }

    }

}
