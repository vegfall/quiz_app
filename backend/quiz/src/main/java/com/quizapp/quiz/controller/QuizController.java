package com.quizapp.quiz.controller;

import com.quizapp.quiz.entity.SessionEntity;
import com.quizapp.quiz.service.SingleplayerQuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/db/{id}")
    public ResponseEntity<SessionEntity> getSessionEntityTest(@PathVariable Long id) {
        return new ResponseEntity<>(quizService.getSessionEntity(id), HttpStatus.OK);
    }

    @PostMapping("/db/{sessionKey}")
    public ResponseEntity<String> postSessionEntityTest(@PathVariable String sessionKey) {
        quizService.saveSessionEntity(sessionKey);
        String returnValue = "SessionEntity with key " + sessionKey + " created";

        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }

    @GetMapping("/session/get-sessions")
    public ResponseEntity<String[]> getSessionKeys() {
        String[] test = new String[2];
        test[0] = quizService.testQuestionCommunication();
        test[1] = quizService.testQuestionCommunicationTwo();

        return new ResponseEntity<>(test, HttpStatus.OK);
    }
}
