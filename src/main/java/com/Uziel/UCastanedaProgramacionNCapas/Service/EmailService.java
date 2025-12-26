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

        String verificationLink = "http://localhost:8080/usuario/verify-account?tokenEmail=" + tokenEmail;

        String htmlContent = """
                             <div style="font-family: Arial, Sans-serif; line-height: 1.5;">
                                <h2> Hola %s </h2>
                                <p> Su registro esta completo, para activar tu cuenta, por favor confirma tu correo.</p>
                                <p>
                                    <a href="%s"
                                        style="display:inline-block; padding: 10px 16px; text-decoration:none; border-radius:6px; background:#1a73e8; color:white;">
                                        Validar correo
                                    </a>
                                </p>
                             </div>
                             """.formatted(nombre, verificationLink);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("verificación de cuenta");
        helper.setText(htmlContent, true);

        mailSender.send(message);

    }

    public void sendPasswordEmail(String toEmail, String nombre, String tokenEmail) throws MessagingException {

        String resetLink = "http://localhost:8080/usuario/reset-password?tokenEmail=" + tokenEmail;

        String htmlContent = """
                            <div style="font-family: Arial, Sans-serif; line-height: 1.5;">
                                <h2> Hola %s </h2>
                                <p> Recibimos una solicitud para restablecer tu contraseña.</p>
                                <p> Este enlace expira en <b>10 minutos</b>. Si tú no hiciste esta solicitud, ignora este correo.</p>
                                <p>
                                    <a href="%s"
                                    style="display:inline-block; padding: 10px 16px; text-decoration:none; border-radius:6px; background:#e53935; color:white;">
                                    Restablecer contraseña
                                    </a>
                                </p>
                            </div>
                            """.formatted(nombre, resetLink);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("verificación de cuenta");
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

}
