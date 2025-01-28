package com.quizapp.quiz.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class QuestionClient {
    private final WebClient webClient;

    public QuestionClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://question/question/")
                .build();
    }

    public String testQuestionCommunication() {
        return webClient
                .get()
                .uri("test")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
