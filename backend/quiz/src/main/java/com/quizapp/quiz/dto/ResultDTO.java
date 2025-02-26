package com.quizapp.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResultDTO {
    private final int correctAlternative;
    private final String explanation;
    private final int score;
}
