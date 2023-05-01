package com.backend1inl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoSuchOrderException extends RuntimeException{
    public NoSuchOrderException(String message) {
        super(message);
    }
}
