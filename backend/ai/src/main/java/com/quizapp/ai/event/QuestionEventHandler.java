package com.quizapp.ai.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@DependsOn("rabbitAdmin")
public class QuestionEventHandler {
    private final RabbitTemplate rabbitTemplate;

    @Value("${amqp.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.ai.response}")
    private String aiResponseQueueName;

    public QuestionEventHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${amqp.queue.ai.request}")
    public void handleAIRequest(String request) {
        log.info("Received AIChatRequest: {}", request);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException error) {
            Thread.currentThread().interrupt();
            log.error("Interrupted during AI thread sleep", error);
        }

        String response = "Hi from AI";

        rabbitTemplate.convertAndSend(exchangeName, aiResponseQueueName, response);

        log.info("Sent AIChatResponse: {}", response);
    }
}
