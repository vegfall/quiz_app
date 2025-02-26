package com.quizapp.result.dto;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
public class ResultDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String flowId;
    private final int correctAlternative;
    private final String explanation;
    private final int score;

    public ResultDTO(int correctAlternative, String explanation, int score) {
        this.flowId = UUID.randomUUID().toString();
        this.correctAlternative = correctAlternative;
        this.explanation = explanation;
        this.score = score;
    }
}
