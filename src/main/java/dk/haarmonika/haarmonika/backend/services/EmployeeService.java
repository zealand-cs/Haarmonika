package dk.haarmonika.haarmonika.backend.services;

import dk.haarmonika.haarmonika.backend.db.daos.EmployeeDao;
import dk.haarmonika.haarmonika.backend.db.daos.IEmployeeDao;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeService implements IEmployeeService {
    private final IEmployeeDao employeeDao;


    public EmployeeService(EmployeeDao dao) {
        this.employeeDao = dao;
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        return employeeDao.getAll(null);
    }

    @Override
    public void createEmployee(Employee employee) throws SQLException {
        employeeDao.save(employee);
    }

    @Override
    public Optional<Employee> getEmployeeById(int id) throws SQLException {
        return employeeDao.get(id);
    }

    @Override
    public void updateEmployee(Employee employee) throws SQLException {
        employeeDao.update(employee);
    }
    @Override
    public void delete(int id) throws SQLException {
        employeeDao.delete(id);
    }

}
