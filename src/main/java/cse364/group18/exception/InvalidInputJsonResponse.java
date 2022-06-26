package cse364.group18.exception;

public class InvalidInputJsonResponse {

    private String error;

    public InvalidInputJsonResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
