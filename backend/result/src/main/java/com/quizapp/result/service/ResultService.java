package com.quizapp.result.service;

import com.quizapp.result.dto.ResultDTO;
import com.quizapp.result.dto.ScoreDTO;
import com.quizapp.result.dto.SessionScoreDTO;
import com.quizapp.result.dto.request.GetResultRequest;

import java.util.List;

public interface ResultService {
    ResultDTO postAnswer(GetResultRequest request);
    ScoreDTO getScore(String sessionKey, String username);
    List<SessionScoreDTO> getScoresForSession(String sessionKey);
    void resetScore(String username, String sessionKey);
}
