package com.quizapp.quiz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @GetMapping("/test")
    public ResponseEntity<String> checkConnection() {
        return new ResponseEntity<>("I am responding OK :)", HttpStatus.OK);
    }
}
