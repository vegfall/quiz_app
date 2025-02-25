package com.quizapp.quiz.mapper;

import com.quizapp.quiz.dto.SessionDTO;
import com.quizapp.quiz.entity.SessionEntity;
import com.quizapp.quiz.model.Session;
import org.springframework.stereotype.Component;

@Component
public class QuizMapper {
    public SessionDTO toDTO(Session model) {
        return new SessionDTO(
                model.getSessionKey(),
                model.getTheme(),
                model.getNumberOfAlternatives(),
                model.getUsername()
        );
    }

    public SessionEntity toEntity(Session model) {
        return new SessionEntity(
                model.getSessionId(),
                model.getSessionKey(),
                model.getTheme(),
                model.getNumberOfAlternatives(),
                model.getUsername(),
                model.getCurrentQuestionKey(),
                model.getStatus()
        );
    }

    public Session toModel(SessionEntity entity) {
        return new Session(
                entity.getSessionId(),
                entity.getSessionKey(),
                entity.getTheme(),
                entity.getNumberOfAlternatives(),
                entity.getUsername(),
                entity.getCurrentQuestionKey(),
                entity.getStatus()
        );
    }
}
