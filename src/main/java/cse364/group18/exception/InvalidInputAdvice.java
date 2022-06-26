package cse364.group18.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class InvalidInputAdvice extends RuntimeException {

    @ExceptionHandler(InvalidInputException.class)
    InvalidInputJsonResponse InvalidInputHandler(InvalidInputException ex) {
        InvalidInputJsonResponse error = new InvalidInputJsonResponse(ex.getMessage());
        return error;
    }
}