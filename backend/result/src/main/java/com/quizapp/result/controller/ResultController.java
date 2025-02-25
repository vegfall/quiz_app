package com.quizapp.result.controller;

import com.quizapp.result.dto.ScoreDTO;
import com.quizapp.result.dto.SessionScoreDTO;
import com.quizapp.result.entity.UserEntity;
import com.quizapp.result.service.SingleplayerResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/result")
public class ResultController {
    private final SingleplayerResultService resultService;

    public ResultController(SingleplayerResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("{sessionKey}/{username}/score")
    public ResponseEntity<ScoreDTO> getScore(@PathVariable String sessionKey, @PathVariable String username) {
        ScoreDTO score = resultService.getScore(sessionKey, username);

        return score != null
                ? new ResponseEntity<>(score, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{sessionKey}/scores")
    public ResponseEntity<List<SessionScoreDTO>> getSessionScores(@PathVariable String sessionKey) {
        List<SessionScoreDTO> scores = resultService.getScoresForSession(sessionKey);

        return scores.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(scores, HttpStatus.OK);
    }
}
