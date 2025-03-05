
package dk.haarmonika.haarmonika.backend.db.validation;

import dk.haarmonika.haarmonika.backend.db.entities.Service;
import dk.haarmonika.haarmonika.backend.exceptions.ServiceValidationException;
import org.springframework.stereotype.Component;

@Component
public class ServiceValidator extends BaseValidator<Service> {

    @Override
    public void validate(Service service) {
        if (service == null) {
            throw new ServiceValidationException("Service cannot be null!");
        }
        validateName(service.getName(), "Name");
    }


}

