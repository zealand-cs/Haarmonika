package dk.haarmonika.haarmonika.backend.services;

import dk.haarmonika.haarmonika.backend.db.daos.employee.IEmployeeDao;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.db.validation.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {
    private final IEmployeeDao employeeDao;
    private final EmployeeValidator employeeValidator;

    @Autowired
    public EmployeeService(@Qualifier("employeeDao") IEmployeeDao employeeDao, EmployeeValidator employeeValidator) {
        this.employeeDao = employeeDao;
        this.employeeValidator = employeeValidator;
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        return employeeDao.getAll(null);
    }

    @Override
    public void createEmployee(Employee employee) throws SQLException {
        employeeValidator.validate(employee);
        employeeDao.save(employee);
    }


    @Override
    public Optional<Employee> getEmployeeById(int id) throws SQLException {
        return employeeDao.get(id);
    }

    @Override
    public void updateEmployee(Employee employee) throws SQLException {
        employeeValidator.validate(employee);
        employeeDao.update(employee);
    }
    @Override
    public void delete(int id) throws SQLException {
        employeeValidator.validateId(id);
        employeeDao.delete(id);
    }

}
