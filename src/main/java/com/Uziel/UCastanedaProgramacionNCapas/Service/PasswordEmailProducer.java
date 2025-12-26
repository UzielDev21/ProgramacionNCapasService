package com.Uziel.UCastanedaProgramacionNCapas.Service;

import com.Uziel.UCastanedaProgramacionNCapas.DTO.PasswordResetEmailMessage;
import jakarta.jms.Queue;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class PasswordEmailProducer {

    private final JmsTemplate jmsTemplate;
    private final Queue passwordEmailQueue;

    public PasswordEmailProducer(JmsTemplate jmsTemplate,
            Queue passwordEmailQueue) {

        this.jmsTemplate = jmsTemplate;
        this.passwordEmailQueue = passwordEmailQueue;
    }
    
    public void senderPasswordEmail(String email, String nombre, String token){
        
        PasswordResetEmailMessage passwordMessage = new PasswordResetEmailMessage();
        
        jmsTemplate.convertAndSend(passwordEmailQueue, passwordMessage);
        
    }

}
