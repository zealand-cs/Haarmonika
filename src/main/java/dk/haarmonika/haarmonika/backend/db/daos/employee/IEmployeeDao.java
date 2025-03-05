package dk.haarmonika.haarmonika.backend.db.daos.employee;

import dk.haarmonika.haarmonika.backend.db.daos.IDao;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeDao extends IDao<Employee> { }
