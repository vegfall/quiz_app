package com.quizapp.quiz.controller;

import com.quizapp.quiz.dto.QuestionDTO;
import com.quizapp.quiz.dto.ResultDTO;
import com.quizapp.quiz.dto.SessionDTO;
import com.quizapp.quiz.dto.SessionScoreDTO;
import com.quizapp.quiz.dto.conclusion.RevealScoreDTO;
import com.quizapp.quiz.dto.request.CreateSessionRequest;
import com.quizapp.quiz.dto.request.LoadSessionRequest;
import com.quizapp.quiz.dto.request.PostAnswerRequest;
import com.quizapp.quiz.model.SessionStatus;
import com.quizapp.quiz.service.SingleplayerQuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/quiz")
public class QuizController {
    private final SingleplayerQuizService quizService;

    public QuizController(SingleplayerQuizService quizService) {
        this.quizService = quizService;
    }

    //Quiz
    @PostMapping("session/create")
    public ResponseEntity<SessionDTO> postNewSession(@RequestBody CreateSessionRequest request) {
        SessionDTO session = quizService.postNewSession(request);

        return session != null
                ? new ResponseEntity<>(session, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("session/{sessionKey}/load")
    public ResponseEntity<SessionDTO> loadPreviousSession(@PathVariable String sessionKey, @RequestBody LoadSessionRequest request) {
        SessionDTO session = quizService.loadPreviousSession(sessionKey, request);

        return session != null
                ? new ResponseEntity<>(session, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("session/{sessionKey}/{username}/start")
    public ResponseEntity<HttpStatus> startSession(@PathVariable String sessionKey, @PathVariable String username) {
        quizService.startSession(sessionKey, username);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("session/{sessionKey}/info")
    public ResponseEntity<SessionDTO> getSessionInfo(@PathVariable String sessionKey) {
        SessionDTO session = quizService.getSession(sessionKey);

        return session != null
                ? new ResponseEntity<>(session, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("session/{sessionKey}/status")
    public ResponseEntity<SessionStatus> getStatus(@PathVariable String sessionKey) {
        return new ResponseEntity<>(quizService.getStatus(sessionKey), HttpStatus.OK);
    }

    @GetMapping("session/get-sessions")
    public ResponseEntity<List<SessionDTO>> getSessions() {
        List<SessionDTO> sessions = quizService.getSessions();

        return new ResponseEntity<>(sessions, HttpStatus.OK);
    }

    //Question
    @GetMapping("session/{sessionKey}/current-question")
    public ResponseEntity<QuestionDTO> getCurrentQuestion(@PathVariable String sessionKey) {
        QuestionDTO question = quizService.getCurrentQuestion(sessionKey);

        return question != null
                ? new ResponseEntity<>(question, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("session/{sessionKey}/next-question")
    public ResponseEntity<HttpStatus> putNextQuestion(@PathVariable String sessionKey) {
        quizService.putNextQuestion(sessionKey);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Result
    @PostMapping("session/{sessionKey}/post-answer")
    public ResponseEntity<ResultDTO> postAnswer(@PathVariable String sessionKey, @RequestBody PostAnswerRequest answer) {
        ResultDTO result = quizService.postAnswer(sessionKey, answer);

        return result != null
                ? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("session/{sessionKey}/{username}/score")
    public ResponseEntity<RevealScoreDTO> getScore(@PathVariable String sessionKey, @PathVariable String username) {
        RevealScoreDTO score = quizService.getScore(sessionKey, username);

        return score != null
                ? new ResponseEntity<>(score, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("session/{sessionKey}/scores")
    public ResponseEntity<List<SessionScoreDTO>> getScores(@PathVariable String sessionKey) {
        List<SessionScoreDTO> sessionScores = quizService.getScores(sessionKey);

        return sessionScores.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(sessionScores, HttpStatus.OK);
    }
}
