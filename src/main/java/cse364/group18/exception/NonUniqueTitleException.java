package cse364.group18.exception;

public class NonUniqueTitleException extends RuntimeException {

    public NonUniqueTitleException(String message, String status) {
        super(message + status);
    }
}
