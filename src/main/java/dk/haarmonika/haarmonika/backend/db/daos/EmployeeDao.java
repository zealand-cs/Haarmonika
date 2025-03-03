package dk.haarmonika.haarmonika.backend.db.daos;

import dk.haarmonika.haarmonika.backend.db.Pagination;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDao extends Dao<Employee> implements IEmployeeDao {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeDao.class);
    static final int roleId = 100; // TODO: id of employee role

    static final String createQuery = "INSERT INTO user (firstName, lastName, email, phone, password, roleId) VALUES (?, ?, ?, ?, ?, ?)";
    static final String readQuery = "SELECT id, firstName, lastName, email, phone, password, roleId FROM user WHERE roleId = " + roleId;
    static final String updateQuery = "UPDATE user SET firstName = ?, lastName = ?, email = ?, phone = ?, password = ?, roleId = ? WHERE id = ?";
    static final String deleteQuery = "DELETE FROM user WHERE id = ?";

    @Override
    public void save(Employee user) throws SQLException {
        logger.info("Saving employee: {}", user);
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(createQuery)) {
        stmt.setString(1, user.getFirstName());
        stmt.setString(2, user.getLastName());
        stmt.setString(3, user.getEmail());
        stmt.setString(4, user.getPhone());
        stmt.setString(5, user.getPassword());
        stmt.setInt(6, roleId);
        int rowsAffected = stmt.executeUpdate();
        logger.info("Save successful, rows affected: {}", rowsAffected);

        }
    }

    @Override
    public Optional<Employee> get(int id) throws SQLException {
        logger.info("Fetching employee with id: {}", id);
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(readQuery + " WHERE id = ?")) {
            stmt.setInt(1, id);
            try (var res = stmt.executeQuery()) {
                if (res.next()) {

                    return Optional.of(fromResultSet(res));
                }
            }
        }
        logger.warn("No employee found with id: {}", id);
        return Optional.empty();
    }


    @Override
    public List<Employee> getAll(Pagination pagination) throws SQLException {
        Pagination safePagination = (pagination != null) ? pagination : new Pagination(0, 10);


        String query = readQuery + " LIMIT ? OFFSET ?";
        try (Connection connection = getConnection();
            var stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, safePagination.perPage());
            stmt.setInt(2, safePagination.perPage() * safePagination.page());

            List<Employee> users = new ArrayList<>();
            try (var res = stmt.executeQuery()) {
                while (res.next()) {
                    users.add(fromResultSet(res));
                }
            }
            return users;
        }
    }

    @Override
    public Employee fromResultSet(ResultSet set) throws SQLException {
        return new Employee(
                set.getInt("id"),
                set.getString("firstName"),
                set.getString("lastName"),
                set.getString("email"),
                set.getString("phone"),
                set.getString("password"),
                set.getInt("roleId")

        );
    }

    @Override
    public void update(Employee user) throws SQLException {
        logger.info("Updating employee: {}", user);
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(updateQuery)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getPassword());
            stmt.setInt(6, user.getRoleId());
            stmt.setInt(7, user.getId());
            int rowsAffected = stmt.executeUpdate();
            logger.info("Update successful, rows affected: {}", rowsAffected);
        }
    }

    @Override
    public void delete(Employee user) throws SQLException {
        logger.info("Deleting user: {}", user);
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(deleteQuery)) {
            stmt.setInt(1, user.getId());
            int rowsAffected = stmt.executeUpdate();
            logger.info("Delete Succesful", rowsAffected);
        }
    }
    //Denne bliver brugt.
    @Override
    public void delete(int id) throws SQLException {
        logger.info("Deleting employee with id: {}", id);
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(deleteQuery)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Delete successful, rows affected: {}", rowsAffected);
            } else {
                logger.warn("No employee found to delete with id: {}", id);
            }
        }
    }
}
