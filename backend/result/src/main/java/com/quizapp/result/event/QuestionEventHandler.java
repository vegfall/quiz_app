package com.quizapp.result.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QuestionEventHandler {
    private final RabbitTemplate rabbitTemplate;

    @Value("${amqp.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.result.response}")
    private String resultResponseQueueName;

    public QuestionEventHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${amqp.queue.result.request}")
    public void handleResultRequest(String request) {
        log.info("Received ResultChatRequest: {}", request);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException error) {
            Thread.currentThread().interrupt();
            log.error("Interrupted during Result thread sleep", error);
        }

        String response = "Hi from Result";

        rabbitTemplate.convertAndSend(exchangeName, resultResponseQueueName, response);

        log.info("Sent ResultChatResponse: {}", response);
    }
}
