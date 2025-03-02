package dk.haarmonika.haarmonika.backend.db.daos;

import dk.haarmonika.haarmonika.backend.db.Pagination;
import dk.haarmonika.haarmonika.backend.db.entities.Customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ICustomerDao {

    List<Customer> getAll(Pagination pagination) throws SQLException;
    void save(Customer customer) throws SQLException;
    Optional<Customer> get(int id) throws SQLException;
    void update(Customer customer) throws SQLException;
    void delete (int id) throws SQLException;
}
