package com.christian.webquizengine.model.exception;

import com.christian.webquizengine.model.quiz.Quiz;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException(Quiz quiz) {
        super(String.format("You are not the author of Quiz ID %d", quiz.getId()));
    }
}
