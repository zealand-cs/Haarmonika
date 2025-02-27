package dk.haarmonika.haarmonika.backend.services;

import dk.haarmonika.haarmonika.backend.db.entities.Employee;

import java.sql.SQLException;
import java.util.Optional;
import java.util.List;

public interface IEmployeeService {

    List<Employee> getAllEmployees() throws SQLException;
    void createEmployee(Employee employee) throws SQLException;
    Optional<Employee> getEmployeeById(int id) throws SQLException;
    void updateEmployee(Employee employee) throws SQLException;
    void delete(int id) throws SQLException;
}
