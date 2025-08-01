package com.example.quiznew.api.utils.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String USER_REGISTER_QUEUE = "user.register.queue";
    public static final String USER_REGISTER_EXCHANGE = "user.register.exchange";
    public static final String USER_REGISTER_ROUTING_KEY = "user.register";

    @Bean
    public Queue userRegisterQueue() {
        return QueueBuilder.durable(USER_REGISTER_QUEUE).build();
    }

    @Bean
    public DirectExchange userRegisterExchange() {
        return new DirectExchange(USER_REGISTER_EXCHANGE);
    }

    @Bean
    public Binding userRegisterBinding(Queue userRegisterQueue, DirectExchange userRegisterExchange) {
        return BindingBuilder.bind(userRegisterQueue).to(userRegisterExchange).with(USER_REGISTER_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
