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
        
        System.out.println("[JMS] mensaje recibido para: " + message.getEmail());
        
        try {
            emailService.sendVerificationEmail(
                    message.getEmail(),
                    message.getNombre(),
                    message.getToken());
            
            System.out.println("[MAIL] correo enviado correamente a: " + message.getEmail());

        } catch (MessagingException ex) {
            System.err.println("[MAIL ERROR] Error enviando correo a" + message.getEmail() + " -> " + ex.getMessage());
        }
    }
}
