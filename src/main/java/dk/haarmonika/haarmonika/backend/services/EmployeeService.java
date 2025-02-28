package dk.haarmonika.haarmonika.backend.services;

import dk.haarmonika.haarmonika.backend.db.daos.EmployeeDao;
import dk.haarmonika.haarmonika.backend.db.daos.IEmployeeDao;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.db.validation.EmployeeValidator;
import dk.haarmonika.haarmonika.backend.exceptions.EmployeeValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {
    private final IEmployeeDao employeeDao;

    @Autowired
    public EmployeeService(@Qualifier("employeedDao") IEmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        return employeeDao.getAll(null);
    }

    @Override
    public void createEmployee(Employee employee) throws SQLException {
        EmployeeValidator.validate(employee);
        employeeDao.save(employee);
    }


    @Override
    public Optional<Employee> getEmployeeById(int id) throws SQLException {
        return employeeDao.get(id);
    }

    @Override
    public void updateEmployee(Employee employee) throws SQLException {
        EmployeeValidator.validate(employee);
        employeeDao.update(employee);
    }
    @Override
    public void delete(int id) throws SQLException {
        EmployeeValidator.validateId(id);
        employeeDao.delete(id);
    }

}
