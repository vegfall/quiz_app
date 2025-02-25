package com.quizapp.question.event;

import com.quizapp.question.dto.ResultDTO;
import com.quizapp.question.dto.request.GetResultRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//https://www.baeldung.com/java-blocking-queue
@Slf4j
@Service
@DependsOn("rabbitAdmin")
public class ResultEventHandler {
    private final RabbitTemplate rabbitTemplate;
    private final BlockingQueue<ResultDTO> resultResponseQueue = new LinkedBlockingQueue<>();

    @Value("${amqp.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.result.request}")
    private String resultRequestQueueName;

    public ResultEventHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public ResultDTO sendGetResultRequest(GetResultRequest request) {
        ResultDTO response = null;
        log.info("Sending GetResultRequest: {}", request);

        rabbitTemplate.convertAndSend(exchangeName, resultRequestQueueName, request);

        try {
            response = resultResponseQueue.take();
            log.info("Received ResultDTO: {}", response);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Failed to retrieve ResultDTO from response queue", e);
        }

        return response;
    }

    @RabbitListener(queues = "${amqp.queue.result.response}")
    public void handleResultResponse(ResultDTO result) {
        log.info("Received ResultDTO from response queue: {}", result);
        boolean offered = resultResponseQueue.offer(result);

        if (!offered) {
            log.warn("Failed to add ResultDTO to the blocking queue. Queue might be full.");
        }
    }
}
