package com.christian.webquizengine.exception;

import com.christian.webquizengine.model.quiz.MyQuiz;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException(long id) {
        super(String.format("You are not the author of Quiz ID %d", id));
    }
}
