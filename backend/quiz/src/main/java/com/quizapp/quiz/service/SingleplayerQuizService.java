package com.quizapp.quiz.service;

import com.quizapp.quiz.client.QuestionClient;
import org.springframework.stereotype.Service;

@Service
public class SingleplayerQuizService implements QuizService {
    private final QuestionClient questionClient;

    public SingleplayerQuizService(QuestionClient questionClient) {
        this.questionClient = questionClient;
    }

    @Override
    public String testQuestionCommunication() {
        return questionClient.testQuestionCommunication();
    }
}
