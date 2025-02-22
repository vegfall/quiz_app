package com.quizapp.quiz.service;

import com.quizapp.quiz.client.QuestionClient;
import com.quizapp.quiz.entity.SessionEntity;
import com.quizapp.quiz.repository.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SingleplayerQuizService implements QuizService {
    private final QuestionClient questionClient;
    private final SessionRepository sessionRepository;

    public SingleplayerQuizService(QuestionClient questionClient, SessionRepository sessionRepository) {
        this.questionClient = questionClient;
        this.sessionRepository = sessionRepository;
    }

    public String testQuestionCommunication() {
        return questionClient.testQuestionCommunication();
    }

    public SessionEntity getSessionEntity(Long id) {
        return sessionRepository.findById(id).orElse(null);
    }

    public void saveSessionEntity(String sessionKey) {
        SessionEntity entity = new SessionEntity();
        entity.setSessionKey(sessionKey);

        log.info("SessionEntity with sessionKey {} created!", sessionKey);

        sessionRepository.save(entity);
    }
}
