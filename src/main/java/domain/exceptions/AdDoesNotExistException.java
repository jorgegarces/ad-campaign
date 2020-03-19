package domain.exceptions;

public class AdDoesNotExistException extends RuntimeException {
    private final String message;

    public AdDoesNotExistException() {
        this.message = "ad does not exist";
    }
}
