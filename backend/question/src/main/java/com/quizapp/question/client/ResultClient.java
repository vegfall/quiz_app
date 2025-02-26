package com.quizapp.question.client;

import com.quizapp.question.dto.ScoreDTO;
import com.quizapp.question.dto.SessionScoreDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ResultClient {
    private final WebClient webClient;

    public ResultClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://result/result/")
                .build();
    }

    public ScoreDTO getScore(String sessionKey, String username) {
        return webClient
                .get()
                .uri("{sessionKey}/{username}/score", sessionKey, username)
                .retrieve()
                .bodyToMono(ScoreDTO.class)
                .block();
    }

    public List<SessionScoreDTO> getScores(String sessionKey) {
        return webClient
                .get()
                .uri("/{sessionKey}/scores", sessionKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<SessionScoreDTO>>() {})
                .block();
    }

    public Void resetScore(String sessionKey, String username) {
        return webClient
                .put()
                .uri("{sessionKey}/{username}/score-reset", sessionKey, username)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
