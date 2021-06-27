package com.christian.webquizengine.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class QuizNotFoundException extends RuntimeException {

    public QuizNotFoundException(long id) {
        super(id < 1 ? "ID should be greater than or equal to 1." :
                String.format("Quiz with the specified ID %d not found.", id));
    }
}
