package com.quizapp.ai.service;

import com.quizapp.ai.dto.request.AIChatRequest;
import com.quizapp.ai.dto.response.AIChatResponse;

public interface AIService {
    AIChatResponse getResponse(AIChatRequest prompt);
}
