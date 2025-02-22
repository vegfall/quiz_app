package com.quizapp.question.service;

import com.quizapp.question.entity.QuestionEntity;
import com.quizapp.question.event.AIEventHandler;
import com.quizapp.question.event.ResultEventHandler;
import com.quizapp.question.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class SingleplayerQuestionService implements QuestionService {
    private final AIEventHandler aiEventHandler;
    private final ResultEventHandler resultEventHandler;
    private final QuestionRepository questionRepository;

    public SingleplayerQuestionService(@Lazy AIEventHandler aiEventHandler, @Lazy ResultEventHandler resultEventHandler, QuestionRepository questionRepository) {
        this.aiEventHandler = aiEventHandler;
        this.resultEventHandler = resultEventHandler;
        this.questionRepository = questionRepository;
    }

    public void asyncAITestSend() {
        CompletableFuture.runAsync(() -> {
            try {
                log.info("Sending AI request asynchronously");
                aiEventHandler.sendAIRequest();
            } catch (Exception error) {
                log.error("Error running async to generate questions", error);
            }
        });
    }

    @Transactional
    public synchronized void asyncAITestReceive(String aiResponse) {
        log.info("YAY FROM SERVICE! HERE IS AI MESSAGE: {}", aiResponse);
    }

    public void asyncResultTestSend() {
        CompletableFuture.runAsync(() -> {
            try {
                log.info("Sending Result request asynchronously");
                resultEventHandler.sendResultRequest();
            } catch (Exception error) {
                log.error("Error running async to generate questions", error);
            }
        });
    }

    @Transactional
    public synchronized void asyncResultTestReceive(String resultResponse) {
        log.info("YAY FROM SERVICE! HERE IS RESULT MESSAGE: {}", resultResponse);
    }

    public QuestionEntity getQuestionEntity(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    public void saveQuestionEntity(String questionText) {
        QuestionEntity entity = new QuestionEntity();
        entity.setQuestionText(questionText);

        log.info("QuestionEntity with questionText {} created!", questionText);

        questionRepository.save(entity);
    }
}
