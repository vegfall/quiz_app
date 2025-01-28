package com.quizapp.quiz.controller;

import com.quizapp.quiz.service.SingleplayerQuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    private final SingleplayerQuizService quizService;

    public QuizController(SingleplayerQuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> checkConnection() {
        return new ResponseEntity<>("Quiz responding OK :)", HttpStatus.OK);
    }

    @GetMapping("/test-question")
    public ResponseEntity<String> testQuestionConnection() {
        String response = quizService.testQuestionCommunication();

        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }
}
