package dk.haarmonika.haarmonika.backend.db.daos;

import dk.haarmonika.haarmonika.backend.db.Pagination;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDao extends Dao<Employee> {
    public EmployeeDao(Connection connection) {
        super(connection);
    }

    static final int roleId = 100; // TODO: id of employee role

    static final String createQuery = "INSERT INTO user (firstName, lastName, email, phone, password, roleId) VALUES (?, ?, ?, ?, ?, ?)";
    static final String readQuery = "SELECT id, firstName, lastName, email, phone, password, roleId FROM user WHERE roleId = " + roleId;
    static final String updateQuery = "UPDATE users SET firstName = ?, lastName = ?, email = ?, phone = ?, password = ?, roleId = ? WHERE id = ?";
    static final String deleteQuery = "DELETE FROM users WHERE id = ?";

    @Override
    public void save(Employee user) throws SQLException {
        var stmt = connection.prepareStatement(createQuery);
        stmt.setString(1, user.firstName);
        stmt.setString(2, user.lastName);
        stmt.setString(3, user.email);
        stmt.setString(4, user.phone);
        stmt.setString(5, user.password);
        stmt.setInt(6, roleId);
        stmt.executeUpdate();
    }

    @Override
    public Optional<Employee> get(int id) throws SQLException {
        var stmt = connection.prepareStatement(readQuery + " WHERE id = ?");
        stmt.setInt(1, id);
        var res = stmt.executeQuery();

        Optional<Employee> user = Optional.empty();
        if (res.next()) {
            user = Optional.ofNullable(fromResultSet(res));
        }

        return user;
    }

    @Override
    public List<Employee> getAll(Pagination pagination) throws SQLException {
        var query = readQuery;
        // Add pagination to query if we want pages
        if (pagination != null) {
            query += " LIMIT " + pagination.perPage + " OFFSET " + pagination.perPage * pagination.page;
        }
        var stmt = connection.prepareStatement(query);
        try (var res = stmt.executeQuery()) {

            List<Employee> users = new ArrayList<>();
            while (res.next()) {
                users.add(fromResultSet(res));
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
                set.getString("password")

        );
    }

    @Override
    public void update(Employee user) throws SQLException {
        var stmt = connection.prepareStatement(updateQuery);
        stmt.setString(1, user.firstName);
        stmt.setString(2, user.lastName);
        stmt.setString(3, user.email);
        stmt.setString(4, user.phone);
        stmt.setInt(5, user.roleId);
        stmt.setInt(6, user.id);
        stmt.executeUpdate();
    }

    @Override
    public void delete(Employee user) throws SQLException {
       connection.prepareStatement(deleteQuery);
    }
}
