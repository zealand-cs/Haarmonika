package dk.haarmonika.haarmonika.backend.db.validation;


import org.springframework.stereotype.Component;

@Component
public abstract class BaseValidator<T> {
/*
    ✅ This regex ensures the name only contains letters (a-z, A-Z, æ, ø, å, Æ, Ø, Å), spaces, and hyphens (-).
    ❌ It excludes numbers, special characters (e.g., @, !, #, etc.), and empty strings.
    */

    private static final String NAME_REGEX = "^[a-zA-ZæøåÆØÅ\\s-]+$";

    /*
    ✅ Must contain exactly one '@'
    ✅ Allows letters (A-Z, a-z), numbers (0-9), and these special characters in the username: ._%+-
    ✅ The domain name must be valid (e.g., example.com)
    ❌ Does not allow invalid characters like !, #, ,, &, *, or spaces
    */

    private static final String EMAIL_REGEX = "^[A-Za-z0-9ÆØÅæøå._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";


    /*
    ✅ Allows digits (0-9), spaces, hyphens (-), and an optional leading '+'
    ✅ Must be between 7 and 15 characters long
    ❌ Excludes letters and special characters (!, @, #, etc.)
    */
    private static final String PHONE_REGEX = "^[+]?\\d[\\d\\s-]{6,14}\\d$";

    public abstract void validate(T entity);

    public void validateId(int id) {
        if (id <= 0) {
            throw new RuntimeException("Invalid ID! ID must be greater than 0.");
        }
    }

    protected void validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty() || !name.matches(NAME_REGEX)) {
            throw new RuntimeException(fieldName + " must contain only letters and cannot be empty!");
        }
    }

    protected void validateEmail(String email) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            throw new RuntimeException("Invalid email format!");
        }
    }

    protected void validatePhone(String phone) {
        if (phone == null || !phone.matches(PHONE_REGEX)) {
            throw new RuntimeException("Invalid phone number format!");
        }
    }

}
