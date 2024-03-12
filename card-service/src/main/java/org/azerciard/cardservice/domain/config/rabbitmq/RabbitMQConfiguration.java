package org.azerciard.cardservice.domain.config.rabbitmq;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    public DirectExchange paymentExchange() {
        return new DirectExchange("paymentExchange");
    }

    @Bean
    public Queue balanceCheckQueue() {
        return new Queue("balanceCheckQueue", false);
    }

    @Bean
    public Binding balanceCheckBinding(DirectExchange paymentExchange, Queue balanceCheckQueue) {
        return BindingBuilder.bind(balanceCheckQueue).to(paymentExchange).with("balance.check");
    }

    @Bean
    public Queue cardResponseQueue() {
        return new Queue("cardResponseQueue", false);
    }

    @Bean
    public Binding cardResponseBinding(DirectExchange paymentExchange, Queue cardResponseQueue) {
        return BindingBuilder.bind(cardResponseQueue).to(paymentExchange).with("card.response");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper jsonMapper) {
        return new Jackson2JsonMessageConverter(jsonMapper);
    }
}