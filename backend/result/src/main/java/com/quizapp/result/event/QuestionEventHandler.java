package com.quizapp.result.event;

import com.quizapp.result.dto.ResultDTO;
import com.quizapp.result.dto.request.GetResultRequest;
import com.quizapp.result.service.SingleplayerResultService;
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
    private final SingleplayerResultService resultService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${amqp.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.result.response}")
    private String resultResponseQueueName;

    public QuestionEventHandler(SingleplayerResultService resultService, RabbitTemplate rabbitTemplate) {
        this.resultService = resultService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "${amqp.queue.result.request}")
    public void handleValidationResponse(GetResultRequest request) {
        ResultDTO result = resultService.postAnswer(request);

        rabbitTemplate.convertAndSend(exchangeName, resultResponseQueueName, result);
    }
}
