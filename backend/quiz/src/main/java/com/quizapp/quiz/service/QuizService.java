package com.quizapp.quiz.service;

import com.quizapp.quiz.dto.QuestionDTO;
import com.quizapp.quiz.dto.ResultDTO;
import com.quizapp.quiz.dto.SessionDTO;
import com.quizapp.quiz.dto.SessionScoreDTO;
import com.quizapp.quiz.dto.conclusion.RevealScoreDTO;
import com.quizapp.quiz.dto.request.CreateSessionRequest;
import com.quizapp.quiz.dto.request.LoadSessionRequest;
import com.quizapp.quiz.dto.request.PostAnswerRequest;
import com.quizapp.quiz.model.SessionStatus;

import java.util.List;

public interface QuizService {
    SessionDTO postNewSession(CreateSessionRequest session);
    SessionDTO getSession(String sessionKey);
    QuestionDTO getCurrentQuestion(String sessionKey);
    void putNextQuestion(String sessionKey);
    ResultDTO postAnswer(String sessionKey, PostAnswerRequest answer);
    RevealScoreDTO getScore(String sessionKey, String username);
    List<SessionScoreDTO> getScores(String sessionKey);
    SessionStatus getStatus(String sessionKey);
    SessionDTO loadPreviousSession(String sessionKey, LoadSessionRequest request);
    List<SessionDTO> getSessions();
    void startSession(String sessionKey, String username);
}

