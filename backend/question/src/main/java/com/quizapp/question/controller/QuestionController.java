package com.quizapp.question.controller;

import com.quizapp.question.service.SingleplayerQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/question")
//https://www.baeldung.com/java-completablefuture-runasync-supplyasync
public class QuestionController {
    private final SingleplayerQuestionService questionService;

    public QuestionController(SingleplayerQuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> checkConnection() {
        return new ResponseEntity<>("Question responding OK :)", HttpStatus.OK);
    }

    @GetMapping("/testrabbitai")
    public ResponseEntity<String> checkConnectionRabbitAI() {
        questionService.asyncAITestSend();

        return new ResponseEntity<>("API Rabbit Worked", HttpStatus.OK);
    }

    @GetMapping("/testrabbitresult")
    public ResponseEntity<String> checkConnectionRabbitResult() {
        questionService.asyncResultTestSend();

        return new ResponseEntity<>("Result Rabbit Worked", HttpStatus.OK);
    }
}
