package dk.haarmonika.haarmonika.backend.db.daos;

import dk.haarmonika.haarmonika.backend.db.Pagination;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IEmployeeDao {

    List<Employee> getAll(Pagination pagination) throws SQLException;
    void save(Employee employee) throws SQLException;
    Optional<Employee> get(int id) throws SQLException;
    void update(Employee employee) throws SQLException;
    void delete(int id) throws SQLException;
}
