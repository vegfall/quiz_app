package com.quizapp.quiz.client;

import com.quizapp.quiz.dto.QuestionDTO;
import com.quizapp.quiz.dto.ResultDTO;
import com.quizapp.quiz.dto.SessionScoreDTO;
import com.quizapp.quiz.dto.conclusion.RevealScoreDTO;
import com.quizapp.quiz.dto.request.NewSessionRequest;
import com.quizapp.quiz.dto.request.PostAnswerRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

//https://www.kapresoft.com/java/2023/10/16/spring-uricomponentsbuilder-best-practices.html
//https://stackoverflow.com/questions/23674046/get-list-of-json-objects-with-spring-resttemplate
//https://www.javacodegeeks.com/2018/03/doing-stuff-with-spring-webflux.html
@Service
public class QuestionClient {
    private final WebClient webClient;

    public QuestionClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://question/question/")
                .build();
    }

    //Sessions
    public Void postSession(NewSessionRequest request) {
        return webClient
            .post()
            .uri("sessions")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }

    //Questions
    public QuestionDTO getQuestion(String sessionKey, Integer questionKey) {
        return webClient
            .get()
            .uri("sessions/{sessionKey}/questions/{questionKey}", sessionKey, questionKey)
            .retrieve()
            .bodyToMono(QuestionDTO.class)
            .block();
    }

    public Boolean checkMoreQuestions(String sessionKey, int currentQuestionKey) {
        return Boolean.TRUE.equals(webClient
                .get()
                .uri("sessions/{sessionKey}/questions/{currentQuestionKey}/has-next", sessionKey, currentQuestionKey)
                .retrieve()
                .bodyToMono(boolean.class)
                .block());
    }

    public ResultDTO postAnswer(String sessionKey, Integer questionKey, PostAnswerRequest answer) {
        return webClient
                .post()
                .uri("sessions/{sessionKey}/questions/{questionKey}/answers", sessionKey, questionKey)
                .bodyValue(answer)
                .retrieve()
                .bodyToMono(ResultDTO.class)
                .block();
    }

    //Result
    public RevealScoreDTO getScore(String sessionKey, String username) {
        return webClient
                .get()
                .uri("sessions/{sessionKey}/users/{username}/score", sessionKey, username)
                .retrieve()
                .bodyToMono(RevealScoreDTO.class)
                .block();
    }

    public List<SessionScoreDTO> getScores(String sessionKey) {
        return webClient
                .get()
                .uri("sessions/{sessionKey}/scores", sessionKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SessionScoreDTO>>() {})
                .block();
    }

    public Void resetScore(String sessionKey, String username) {
        return webClient
                .put()
                .uri("sessions/{sessionKey}/users/{username}/score/reset", sessionKey, username)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
