package com.backend1inl.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleBasicException(Exception ex, WebRequest request) {
        ErrorDetails details = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();

        return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Custom exception for failed JPA validation
    @ExceptionHandler(TransactionSystemException.class)
    public final ResponseEntity<ErrorDetails> handleTransactionSystemException(WebRequest request) {
        // Our own custom exception object
        ErrorDetails details = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message("The data that has been passed is not be valid. Please try again. Check API documentation for full information on valid parameters.")
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NoSuchCustomerException.class)
    public final ResponseEntity<ErrorDetails> handleNoSuchCustomerException(Exception ex, WebRequest request) {
        ErrorDetails details = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchItemException.class)
    public final ResponseEntity<ErrorDetails> handleNoSuchItemException(Exception ex, WebRequest request) {
        ErrorDetails details = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();

        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPriceException.class)
    public final ResponseEntity<ErrorDetails> handleInvalidPriceException(Exception ex, WebRequest request) {
        ErrorDetails details = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchOrderException.class)
    public final ResponseEntity<ErrorDetails> handleNoSuchOrderException(Exception ex, WebRequest request) {
        ErrorDetails details = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidItemNameException.class)
    public final ResponseEntity<ErrorDetails> invalidItemNameException(Exception ex, WebRequest request) {
        ErrorDetails details = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        int errorCount = ex.getErrorCount();

        String errorMsg = ex.getFieldErrors()
                .stream().map(fieldError -> fieldError.getDefaultMessage() + " ")
                .collect(Collectors.joining());

        ErrorDetails details = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message("Error Count: " + errorCount + " " + errorMsg)
                .details(request.getDescription(false))
                .build();

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }


}
