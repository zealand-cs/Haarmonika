package dk.haarmonika.haarmonika.backend.exceptions;

public class EmployeeValidationException extends RuntimeException {
    public EmployeeValidationException(String message) {
        super(message);
    }
}
