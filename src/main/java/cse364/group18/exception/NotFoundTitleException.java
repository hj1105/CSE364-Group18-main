package cse364.group18.exception;

public class NotFoundTitleException extends RuntimeException {

    public NotFoundTitleException(String message, String status) {
        super(message + status);
    }
}