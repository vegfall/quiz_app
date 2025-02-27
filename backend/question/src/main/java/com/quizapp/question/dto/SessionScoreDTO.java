package com.quizapp.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionScoreDTO {
    private final String username;
    private int totalScore;
    private int numberOfQuestions;
}
