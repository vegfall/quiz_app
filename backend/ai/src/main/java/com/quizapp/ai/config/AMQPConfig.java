package com.quizapp.ai.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class AMQPConfig {
    @Value("${amqp.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.ai.request}")
    private String aiRequestQueue;

    @Value("${amqp.queue.ai.response}")
    private String aiResponseQueue;

    @Bean
    public TopicExchange aiExchange() {
        return ExchangeBuilder.topicExchange(exchangeName)
                .durable(true)
                .build();
    }

    @Bean
    public Queue aiRequestQueue() {
        return QueueBuilder.durable(aiRequestQueue).build();
    }

    @Bean
    public Queue aiResponseQueue() {
        return QueueBuilder.durable(aiResponseQueue).build();
    }

    @Bean
    public Binding aiRequestBinding(Queue aiRequestQueue, TopicExchange aiExchange) {
        return BindingBuilder.bind(aiRequestQueue).to(aiExchange).with("ai.queue.request");
    }

    @Bean
    public Binding aiResponseBinding(Queue aiResponseQueue, TopicExchange aiExchange) {
        return BindingBuilder.bind(aiResponseQueue).to(aiExchange).with("ai.queue.response");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    @Bean
    public Object forceRabbitAdminInit(RabbitAdmin rabbitAdmin, ConnectionFactory connectionFactory) throws IOException, TimeoutException {
        connectionFactory.createConnection().createChannel(false).close();
        return new Object();
    }
}
