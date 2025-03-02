package dk.haarmonika.haarmonika.backend.services;

import dk.haarmonika.haarmonika.backend.db.entities.Customer;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ICustomerService {

    List<Customer> getAllCustomers() throws SQLException;
    void createCustomer(Customer customer) throws SQLException;
    Optional<Customer> getCustomerById(int id) throws SQLException;
    void updateCustomer(Customer customer) throws SQLException;
    void delete(int id) throws SQLException;
}
