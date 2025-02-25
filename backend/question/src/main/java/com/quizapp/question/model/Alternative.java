package com.quizapp.question.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Alternative {
    private Long alternativeId;
    private int alternativeKey;
    private String alternativeText;
    private boolean correct;
    private String alternativeExplanation;
}
