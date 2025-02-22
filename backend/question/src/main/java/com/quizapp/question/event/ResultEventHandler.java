package com.quizapp.question.event;

import com.quizapp.question.service.SingleplayerQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service
public class ResultEventHandler {
    private final RabbitTemplate rabbitTemplate;
    private final SingleplayerQuestionService questionService;
    private final BlockingQueue<String> resultResponseQueue = new LinkedBlockingQueue<>();

    @Value("${amqp.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.result.request}")
    private String resultRequestQueueName;

    public ResultEventHandler(RabbitTemplate rabbitTemplate, SingleplayerQuestionService questionService) {
        this.rabbitTemplate = rabbitTemplate;
        this.questionService = questionService;
    }

    public void sendResultRequest() {
        String request = "This is Question saying Hi to Result";

        rabbitTemplate.convertAndSend(exchangeName, resultRequestQueueName, request);
    }

    @RabbitListener(queues = "${amqp.queue.result.response}")
    public void handleResultResponse(String response) {
        log.info("Received ResultChatResponse from Result response queue: {}", response);
        boolean offered = resultResponseQueue.offer(response);

        log.info("YAY IT WORKED :)");
        questionService.asyncResultTestReceive(response);

        if (!offered) {
            log.warn("Failed to add ResultChatResponse to the blocking queue. Queue might be full.");
        }
    }
}
