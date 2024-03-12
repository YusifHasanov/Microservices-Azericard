package org.azerciard.productservice.domain.config.rabbitmq;


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
    public Queue paymentRequestQueue() {
        return new Queue("paymentRequestQueue", false);
    }

    @Bean
    public Binding paymentRequestBinding(DirectExchange paymentExchange, Queue paymentRequestQueue) {
        return BindingBuilder.bind(paymentRequestQueue).to(paymentExchange).with("product.stock.check");
    }

    @Bean
    public Queue stockCheckQueue() {
        return new Queue("stockCheckQueue", false);
    }

    @Bean
    public Binding productResponseBinding(DirectExchange paymentExchange, Queue stockCheckQueue) {
        return BindingBuilder.bind(stockCheckQueue).to(paymentExchange).with("balance.success");
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