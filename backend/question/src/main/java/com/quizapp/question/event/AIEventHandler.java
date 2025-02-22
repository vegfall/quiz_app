package com.quizapp.question.event;

import com.quizapp.question.service.SingleplayerQuestionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service
public class AIEventHandler {
    private final RabbitTemplate rabbitTemplate;
    private final SingleplayerQuestionService questionService;
    private final BlockingQueue<String> aiResponseQueue = new LinkedBlockingQueue<>();

    @Value("${amqp.ai.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.ai.request}")
    private String aiRequestQueueName;

    public AIEventHandler(RabbitTemplate rabbitTemplate, SingleplayerQuestionService questionService) {
        this.rabbitTemplate = rabbitTemplate;
        this.questionService = questionService;
    }

    public void sendAIRequest() {
        String request = "This is Question saying Hi to AI";

        rabbitTemplate.convertAndSend(exchangeName, aiRequestQueueName, request);
    }

    @RabbitListener(queues = "${amqp.queue.ai.response}")
    public void handleAIResponse(String response) {
        log.info("Received AIChatResponse from AI response queue: {}", response);
        boolean offered = aiResponseQueue.offer(response);

        log.info("YAY IT WORKED :)");
        questionService.asyncAITestReceive(response);

        if (!offered) {
            log.warn("Failed to add AIChatResponse to the blocking queue. Queue might be full.");
        }
    }
}
