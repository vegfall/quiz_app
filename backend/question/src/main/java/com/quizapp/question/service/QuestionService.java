package com.quizapp.question.service;

import com.quizapp.question.dto.QuestionDTO;
import com.quizapp.question.dto.ResultDTO;
import com.quizapp.question.dto.SessionScoreDTO;
import com.quizapp.question.dto.conclusion.RevealScoreDTO;
import com.quizapp.question.dto.request.NewSessionRequest;
import com.quizapp.question.dto.request.PostAnswerRequest;

import java.util.List;

public interface QuestionService {
    QuestionDTO getQuestion(String sessionKey, Integer questionKey);
    ResultDTO postAnswer(String sessionKey, Integer questionKey, PostAnswerRequest answer);
    RevealScoreDTO getScore(String sessionKey, String username);
    List<SessionScoreDTO> getScores(String sessionKey);
    boolean checkMoreQuestions(String sessionKey, int currentQuestionKey);
    void postSession(NewSessionRequest session);
    void saveAIQuestions(String sessionKey, String aiResponse);
}
