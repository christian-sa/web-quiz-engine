package com.christian.webquizengine.model.quiz;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizResult {

    boolean success;
    String feedback;

    public QuizResult(boolean success) {
        this.success = success;
        this.feedback = success ? "Congratulations, you're right!" : "Wrong answer! Please, try again.";
    }
}
