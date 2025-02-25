package com.quizapp.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SessionDTO {
    private final String sessionKey;
    private final String theme;
    private final int numberOfAlternatives;
    private final String username;
}
