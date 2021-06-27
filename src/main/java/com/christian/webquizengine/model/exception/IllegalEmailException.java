package com.christian.webquizengine.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IllegalEmailException extends RuntimeException {

    public IllegalEmailException(String email) {
        super(String.format("Email %s is invalid.", email));
    }
}