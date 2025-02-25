package com.quizapp.ai.service;

import com.quizapp.ai.dto.request.AIChatRequest;
import com.quizapp.ai.dto.request.ChatGPTRequest;
import com.quizapp.ai.dto.response.AIChatResponse;
import com.quizapp.ai.dto.response.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SingleplayerAIService implements AIService {
    private final RestTemplate restTemplate;

    @Value("${openai.api.url}")
    private String openAIUrl;

    @Value("${openai.model}")
    private String model;

    public SingleplayerAIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public AIChatResponse getResponse(AIChatRequest prompt) {
        AIChatResponse responseMessage;
        ChatGPTRequest request = new ChatGPTRequest(model, prompt.getPrompt());
        HttpEntity<ChatGPTRequest> entity = new HttpEntity<>(request);

        ResponseEntity<ChatGPTResponse> response = restTemplate.exchange(
                openAIUrl, HttpMethod.POST, entity, ChatGPTResponse.class
        );

        if (response.getBody() != null && !response.getBody().getChoices().isEmpty()) {
            responseMessage = new AIChatResponse(prompt.getSessionKey(), prompt.getPrompt(), response.getBody().getChoices().getFirst().getMessage().getContent());
        } else {
            responseMessage = new AIChatResponse(prompt.getSessionKey(), prompt.getPrompt(), "No response from OpenAI...");
        }

        return responseMessage;
    }
}
