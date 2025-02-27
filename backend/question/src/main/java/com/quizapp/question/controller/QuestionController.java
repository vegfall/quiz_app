package com.quizapp.question.controller;

import com.quizapp.question.dto.QuestionDTO;
import com.quizapp.question.dto.ResultDTO;
import com.quizapp.question.dto.SessionScoreDTO;
import com.quizapp.question.dto.conclusion.RevealScoreDTO;
import com.quizapp.question.dto.request.NewSessionRequest;
import com.quizapp.question.dto.request.PostAnswerRequest;
import com.quizapp.question.service.SingleplayerQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/question")
//https://www.baeldung.com/java-completablefuture-runasync-supplyasync
public class QuestionController {
    private final SingleplayerQuestionService questionService;

    public QuestionController(SingleplayerQuestionService questionService) {
        this.questionService = questionService;
    }

    //Session
    @PostMapping("sessions")
    public ResponseEntity<HttpStatus> postSession(@RequestBody NewSessionRequest request) {
        questionService.postSession(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Question
    @GetMapping("/sessions/{sessionKey}/questions/{questionKey}")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable String sessionKey, @PathVariable Integer questionKey) {
        QuestionDTO question = questionService.getQuestion(sessionKey, questionKey);

        return question != null
                ? new ResponseEntity<>(question, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/sessions/{sessionKey}/questions/{currentQuestionKey}/has-next")
    public ResponseEntity<Boolean> checkMoreQuestions(@PathVariable String sessionKey, @PathVariable Integer currentQuestionKey) {
        return new ResponseEntity<>(questionService.checkMoreQuestions(sessionKey, currentQuestionKey), HttpStatus.OK);
    }

    //Result
    @PostMapping("sessions/{sessionKey}/questions/{questionKey}/answers")
    public ResponseEntity<ResultDTO> postAnswer(@PathVariable String sessionKey, @PathVariable Integer questionKey, @RequestBody PostAnswerRequest answer) {
        ResultDTO result = questionService.postAnswer(sessionKey, questionKey, answer);

        return result != null
                ? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/sessions/{sessionKey}/users/{username}/score")
    public ResponseEntity<RevealScoreDTO> getScore(@PathVariable String sessionKey, @PathVariable String username) {
        RevealScoreDTO score = questionService.getScore(sessionKey, username);

        return score != null
                ? new ResponseEntity<>(score, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("sessions/{sessionKey}/scores")
    public ResponseEntity<List<SessionScoreDTO>> getScores(@PathVariable String sessionKey) {
        List<SessionScoreDTO> sessionScores = questionService.getScores(sessionKey);

        return sessionScores.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(sessionScores, HttpStatus.OK);
    }

    @PutMapping("sessions/{sessionKey}/users/{username}/score/reset")
    public ResponseEntity<HttpStatus> resetScore(@PathVariable String sessionKey, @PathVariable String username) {
        questionService.resetScore(sessionKey, username);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
