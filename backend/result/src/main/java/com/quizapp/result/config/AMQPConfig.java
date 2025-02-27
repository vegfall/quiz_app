package com.quizapp.result.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
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
//https://stackoverflow.com/questions/72308328/spring-amqp-rabbitadmin-unable-to-recreate-queue-when-it-was-manually-deleted?utm_source=chatgpt.com
@Slf4j
@Configuration
public class AMQPConfig {
    @Value("${amqp.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.result.request}")
    private String resultRequestQueueName;

    @Value("${amqp.queue.result.response}")
    private String resultResponseQueueName;

    @Bean
    public TopicExchange resultExchange() {
        return ExchangeBuilder.topicExchange(exchangeName)
                .durable(true)
                .build();
    }

    @Bean
    public Queue resultRequestQueue() {
        return QueueBuilder.durable(resultRequestQueueName).build();
    }

    @Bean
    public Queue resultResponseQueue() {
        Queue q = QueueBuilder.durable(resultResponseQueueName).build();
        System.out.println("Creating queue: " + resultResponseQueueName);
        return q;
    }

    @Bean
    public Binding resultRequestBinding(Queue resultRequestQueue, TopicExchange resultExchange) {
        return BindingBuilder.bind(resultRequestQueue)
                .to(resultExchange)
                .with("result.queue.request");
    }

    @Bean
    public Binding resultResponseBinding(Queue resultResponseQueue, TopicExchange resultExchange) {
        return BindingBuilder.bind(resultResponseQueue)
                .to(resultExchange)
                .with("result.queue.response");
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
