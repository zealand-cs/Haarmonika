package dk.haarmonika.haarmonika.backend.db.validation;

import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.exceptions.EmployeeValidationException;
import org.springframework.stereotype.Component;


@Component
public class EmployeeValidator extends BaseValidator<Employee>{

    @Override
    public void validate(Employee employee) {
        if (employee == null) {
            throw new EmployeeValidationException("Employee cannot be null!");
        }
        validateName(employee.getFirstName(), "First name");
        validateName(employee.getLastName(), "Last name");
        validateEmail(employee.getEmail());
        validatePhone(employee.getPhone());
    }


}
