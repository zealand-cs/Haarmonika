package dk.haarmonika.haarmonika.backend.exceptions;

public class CustomerValidationException extends RuntimeException {
    public CustomerValidationException(String message) {
        super(message);
    }
}
