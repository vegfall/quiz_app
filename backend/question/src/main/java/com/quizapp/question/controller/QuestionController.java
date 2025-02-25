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
    @PostMapping("post-session")
    public ResponseEntity<HttpStatus> postSession(@RequestBody NewSessionRequest request) {
        questionService.postSession(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Question
    @GetMapping("{sessionKey}/{questionKey}")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable String sessionKey, @PathVariable Integer questionKey) {
        QuestionDTO question = questionService.getQuestion(sessionKey, questionKey);

        return question != null
                ? new ResponseEntity<>(question, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{sessionKey}/{currentQuestionKey}/check-more")
    public ResponseEntity<Boolean> checkMoreQuestions(@PathVariable String sessionKey, @PathVariable Integer currentQuestionKey) {
        return new ResponseEntity<>(questionService.checkMoreQuestions(sessionKey, currentQuestionKey), HttpStatus.OK);
    }

    //Result
    @PostMapping("{sessionKey}/{questionKey}/post-answer")
    public ResponseEntity<ResultDTO> postAnswer(@PathVariable String sessionKey, @PathVariable Integer questionKey, @RequestBody PostAnswerRequest answer) {
        ResultDTO result = questionService.postAnswer(sessionKey, questionKey, answer);

        return result != null
                ? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{sessionKey}/{username}/score")
    public ResponseEntity<RevealScoreDTO> getScore(@PathVariable String sessionKey, @PathVariable String username) {
        RevealScoreDTO score = questionService.getScore(sessionKey, username);

        return score != null
                ? new ResponseEntity<>(score, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{sessionKey}/scores")
    public ResponseEntity<List<SessionScoreDTO>> getScores(@PathVariable String sessionKey) {
        List<SessionScoreDTO> sessionScores = questionService.getScores(sessionKey);

        return sessionScores.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(sessionScores, HttpStatus.OK);
    }
}
