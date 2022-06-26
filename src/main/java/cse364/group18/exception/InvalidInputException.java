package cse364.group18.exception;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message, String status) {
        super(message + status);
    }
}