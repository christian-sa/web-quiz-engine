package com.christian.webquizengine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IllegalPasswordException extends RuntimeException {

    public IllegalPasswordException() {}
}
