package com.labs.maven.springBoot.SpringBootMSC.Messaging;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${jsa.rabbitmq.queue.createdtype}")
    String queueCreatedName;
    @Bean
    Queue queueForCreate() {
        return new Queue(queueCreatedName, false);
    }

    @Value("${jsa.rabbitmq.queue.gettype}")
    String queueGetName;
    @Bean
    Queue queueForGet() {
        return new Queue(queueGetName, false);
    }

    @Value("${jsa.rabbitmq.queue.deletedtype}")
    String queueDeletedName;
    @Bean
    Queue queueForDelete() {
        return new Queue(queueDeletedName, false);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory("localhost", 5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory;
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }
}
