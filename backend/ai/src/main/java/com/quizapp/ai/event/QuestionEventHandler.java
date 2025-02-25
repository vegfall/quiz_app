package com.quizapp.ai.event;

import com.quizapp.ai.dto.request.AIChatRequest;
import com.quizapp.ai.dto.response.AIChatResponse;
import com.quizapp.ai.service.SingleplayerAIService;
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
    private final SingleplayerAIService aiService;

    @Value("${amqp.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.ai.response}")
    private String aiResponseQueueName;

    public QuestionEventHandler(RabbitTemplate rabbitTemplate, SingleplayerAIService aiService) {
        this.rabbitTemplate = rabbitTemplate;
        this.aiService = aiService;
    }

    @RabbitListener(queues = "${amqp.queue.ai.request}")
    public void handleAIRequest(AIChatRequest request) {
        log.info("Received AIChatRequest: {}", request);

        AIChatResponse response = aiService.getResponse(request);

        rabbitTemplate.convertAndSend(exchangeName, aiResponseQueueName, response);

        log.info("Sent AIChatResponse: {}", response);
    }
}
