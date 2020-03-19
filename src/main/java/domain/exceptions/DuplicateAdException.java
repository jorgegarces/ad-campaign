package domain.exceptions;

public class DuplicateAdException extends RuntimeException {

    private final String message;

    public DuplicateAdException() {
        this.message = "ad with same title and description already exists";
    }
}