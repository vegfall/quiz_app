package com.quizapp.question.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//https://www.rabbitmq.com/tutorials/tutorial-four-spring-amqp
@Slf4j
@Configuration
public class AMQPConfig {
    @Value("${amqp.exchange.name}")
    private String resultExchangeName;

    @Value("${amqp.ai.exchange.name}")
    private String aiExchangeName;

    @Value("${amqp.queue.ai.response}")
    private String aiResponseQueue;

    @Bean
    public TopicExchange resultExchange() {
        return ExchangeBuilder.topicExchange(resultExchangeName)
                .durable(true)
                .build();
    }

    @Bean
    public TopicExchange aiExchange() {
        return ExchangeBuilder.topicExchange(aiExchangeName)
                .durable(true)
                .build();
    }

    @Bean
    public Queue aiResponseQueue() {
        return QueueBuilder.durable(aiResponseQueue).build();
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
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    @Bean
    public Object forceRabbitAdminInit(RabbitAdmin rabbitAdmin, ConnectionFactory connectionFactory) {
        try (Connection connection = connectionFactory.createConnection()){
            Channel channel =  connection.createChannel(false);
        } catch (Exception error) {
            log.error("Failed to initialize RabbitMQ admin", error);
        }

        return new Object();
    }
}
