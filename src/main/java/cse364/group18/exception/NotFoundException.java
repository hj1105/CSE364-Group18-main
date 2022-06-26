package cse364.group18.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message, String status) {
        super(message + status);
    }
}
