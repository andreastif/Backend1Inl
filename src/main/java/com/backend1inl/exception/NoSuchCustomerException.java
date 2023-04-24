package com.backend1inl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NoSuchCustomerException extends RuntimeException {
    public NoSuchCustomerException(String message) {
        super(message);
    }
}
