package dk.haarmonika.haarmonika.backend.db.daos.employee;

import dk.haarmonika.haarmonika.backend.db.daos.IDao;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

@Repository
public interface IEmployeeDao extends IDao<Employee> {
    void delete(int id) throws SQLException;
    boolean validateEmployee(String email, String password) throws SQLException;
}
