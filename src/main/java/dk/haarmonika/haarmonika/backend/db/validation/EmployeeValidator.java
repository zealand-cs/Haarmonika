package dk.haarmonika.haarmonika.backend.db.validation;

import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.exceptions.EmployeeValidationException;

public class EmployeeValidator {

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

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    /*
    ✅ Allows digits (0-9), spaces, hyphens (-), and an optional leading '+'
    ✅ Must be between 7 and 15 characters long
    ❌ Excludes letters and special characters (!, @, #, etc.)
    */
    private static final String PHONE_REGEX = "^[+]?\\d[\\d\\s-]{6,14}\\d$";

    public static void validate(Employee employee) {
        if (employee == null) {
            throw new EmployeeValidationException("Employee cannot be null!");
        }
        validateName(employee.getFirstName(), "First name");
        validateName(employee.getLastName(), "Last name");
        validateEmail(employee.getEmail());
        validatePhone(employee.getPhone());
    }

    public static void validateId(int id) {
        if (id <= 0) {
            throw new EmployeeValidationException("Invalid employee ID! ID must be greater than 0.");
        }
    }



    private static void validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty() || !name.matches(NAME_REGEX)) {
            throw new EmployeeValidationException(fieldName + " must contain only letters and cannot be empty!");
        }
    }

    private static void validateEmail(String email) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            throw new EmployeeValidationException("Invalid email format!");
        }
    }

    private static void validatePhone(String phone) {
        if (phone == null || !phone.matches(PHONE_REGEX)) {
            throw new EmployeeValidationException("Invalid phone number format!");
        }
    }


}
