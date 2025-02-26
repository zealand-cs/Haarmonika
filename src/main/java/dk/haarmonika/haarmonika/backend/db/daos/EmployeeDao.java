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
    private Pagination pagination = new Pagination();
    static final int roleId = 100; // TODO: id of employee role

    static final String createQuery = "INSERT INTO user (firstName, lastName, email, phone, password, roleId) VALUES (?, ?, ?, ?, ?, ?)";
    static final String readQuery = "SELECT id, firstName, lastName, email, phone, password, roleId FROM user WHERE roleId = " + roleId;
    static final String updateQuery = "UPDATE user SET firstName = ?, lastName = ?, email = ?, phone = ?, password = ?, roleId = ? WHERE id = ?";
    static final String deleteQuery = "DELETE FROM user WHERE id = ?";

    @Override
    public void save(Employee user) throws SQLException {
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(createQuery)) {
        stmt.setString(1, user.getFirstName());
        stmt.setString(2, user.getLastName());
        stmt.setString(3, user.getEmail());
        stmt.setString(4, user.getPhone());
        stmt.setString(5, user.getPassword());
        stmt.setInt(6, roleId);
        stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Employee> get(int id) throws SQLException {
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(readQuery + " WHERE id = ?")) {
            stmt.setInt(1, id);
            try (var res = stmt.executeQuery()) {
                if (res.next()) {
                    return Optional.of(fromResultSet(res));
                }
            }
        }
        return Optional.empty();
    }


    @Override
    public List<Employee> getAll(Pagination pagination) throws SQLException {
        if (pagination == null) {
            pagination = new Pagination();
        }

        String query = readQuery + " LIMIT ? OFFSET ?";
        try (Connection connection = getConnection();
            var stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, pagination.perPage);
            stmt.setInt(2, pagination.perPage * pagination.page);

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
                set.getString("password")

        );
    }

    @Override
    public void update(Employee user) throws SQLException {
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(updateQuery)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setInt(5, user.getRoleId());
            stmt.setInt(6, user.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Employee user) throws SQLException {
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(deleteQuery)) {
            stmt.setInt(1, user.getId());
            stmt.executeUpdate();
        }
    }
}
