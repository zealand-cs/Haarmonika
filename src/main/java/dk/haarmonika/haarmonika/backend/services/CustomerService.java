package dk.haarmonika.haarmonika.backend.services;

import dk.haarmonika.haarmonika.backend.db.daos.CustomerDao;
import dk.haarmonika.haarmonika.backend.db.entities.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(CustomerDao dao) {
        this.customerDao = dao;
    }

    public List<Customer> getAllCustomer() throws SQLException {
        return customerDao.getAll(null);
    }

    public void createEmployee(Customer customer) throws SQLException {
        customerDao.save(customer);
    }
}
