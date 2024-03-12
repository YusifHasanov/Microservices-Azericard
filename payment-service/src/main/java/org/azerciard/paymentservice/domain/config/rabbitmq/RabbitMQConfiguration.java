package org.azerciard.paymentservice.domain.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    public DirectExchange paymentExchange() {
        return new DirectExchange("paymentExchange");
    }

    @Bean
    public Queue paymentRequestQueue() {
        return new Queue("paymentRequestQueue", false);
    }

    @Bean
    public Binding paymentRequestBinding(DirectExchange paymentExchange, Queue paymentRequestQueue) {
        return BindingBuilder.bind(paymentRequestQueue).to(paymentExchange).with("product.stock.check");
    }

    // For listening to the outcome from the Card Service
    @Bean
    public Queue finishPayment() {
        return new Queue("finishPayment", false);
    }

    @Bean
    public Binding paymentOutcomeBinding(DirectExchange paymentExchange, Queue finishPayment) {
        return BindingBuilder.bind(finishPayment).to(paymentExchange).with("stock.update.success");
    }
       @Bean
    public Queue paymentFailure() {
        return new Queue("paymentFailure", false);
    }

    @Bean
    public Binding paymentFailureBinding(DirectExchange paymentExchange, Queue paymentFailure) {
        return BindingBuilder.bind(paymentFailure).to(paymentExchange).with("payment.failure");
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