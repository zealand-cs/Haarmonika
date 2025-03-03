

package dk.haarmonika.haarmonika.backend.services;

import dk.haarmonika.haarmonika.backend.db.daos.ICustomerDao;
import dk.haarmonika.haarmonika.backend.db.entities.Customer;
import dk.haarmonika.haarmonika.backend.db.validation.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Service
public class CustomerService implements ICustomerService {
    private final ICustomerDao customerDao;
    private final CustomerValidator customerValidator;

    @Autowired
    public CustomerService(@Qualifier("customerDao") ICustomerDao customerDao, CustomerValidator customerValidator) {
        this.customerDao = customerDao;
        this.customerValidator = customerValidator;
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        return customerDao.getAll(null);
    }

    @Override
    public void createCustomer(Customer customer) throws SQLException {
        customerValidator.validate(customer);
        customerDao.save(customer);
    }

    @Override
    public Optional<Customer> getCustomerById(int id) throws SQLException {
        return customerDao.get(id);
    }

    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        customerValidator.validate(customer);
        customerDao.update(customer);
    }

    @Override
    public void delete(int id) throws SQLException {
        customerValidator.validateId(id);
        customerDao.delete(id);
    }
}


