package com.Uziel.UCastanedaProgramacionNCapas.Configuration;

import jakarta.jms.Queue;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class EmbeddedActiveMQConfiguration {

    @Bean
    public Queue verificationEmailQueue(){
        return new ActiveMQQueue("queue.email.verification");
    }
    
    @Bean
    public ActiveMQConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL("vm://embedded?broker.persistent=false");
        factory.setTrustAllPackages(true);
        return factory;
    }
    
    @Bean
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDeliveryPersistent(false);
        return jmsTemplate;
    }
    
}
