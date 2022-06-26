package cse364.group18.exception;


public class NotFoundYearException extends RuntimeException {

    public NotFoundYearException(String message, String status) {
        super(message + status);
    }
}
