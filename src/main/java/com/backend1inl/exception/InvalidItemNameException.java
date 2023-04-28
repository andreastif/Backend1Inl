package com.backend1inl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidItemNameException extends RuntimeException{
    public InvalidItemNameException(String message) {
        super(message);
    }
}
