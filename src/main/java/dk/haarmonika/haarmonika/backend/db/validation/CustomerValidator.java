
package dk.haarmonika.haarmonika.backend.db.validation;

import dk.haarmonika.haarmonika.backend.db.entities.Customer;
import dk.haarmonika.haarmonika.backend.exceptions.CustomerValidationException;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidator extends BaseValidator<Customer>{
    @Override
    public void validate(Customer customer) {
        if (customer == null) {
            throw new CustomerValidationException("Customer cannot be null!");
        }
        validateName(customer.getFirstName(), "First name");
        validateName(customer.getLastName(), "Last name");
        validateEmail(customer.getEmail());
        validatePhone(customer.getPhone());
    }
}

