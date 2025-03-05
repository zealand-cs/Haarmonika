
package dk.haarmonika.haarmonika.backend.db.daos.customer;
import dk.haarmonika.haarmonika.backend.db.Pagination;
import dk.haarmonika.haarmonika.backend.db.daos.Dao;
import dk.haarmonika.haarmonika.backend.db.entities.Customer;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerDao extends Dao<Customer> implements ICustomerDao {
    static final int roleId = 50; // TODO: id of customer role
    private static final Logger logger = LoggerFactory.getLogger(CustomerDao.class);

    static final String createQuery = "INSERT INTO user (firstName, lastName, email, phone, password, roleId) VALUES (?, ?, ?, ?, ?, ?)";
    static final String readQuery = "SELECT id, firstName, lastName, email, phone, password, roleId FROM user WHERE roleId = " + roleId;
    static final String updateQuery = "UPDATE user SET firstName = ?, lastName = ?, email = ?, phone = ?, password = ?, roleId = ? WHERE id = ?";
    static final String deleteQuery = "DELETE FROM user WHERE id = ?";

    @Override
    public void save(Customer user) throws SQLException {
        logger.info("Saving customer: {}", user);
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(createQuery)) {
             stmt.setString(1, user.getFirstName());
             stmt.setString(2, user.getLastName());
             stmt.setString(3, user.getEmail());
             stmt.setString(4, user.getPhone());
             stmt.setString(5, user.getPassword());
             stmt.setInt(6, roleId);
             int rowsAffected = stmt.executeUpdate();
             logger.info("Customer Creation Successful, rows affected: {}", rowsAffected);
        }
    }

    @Override
    public Optional<Customer> get(int id) throws SQLException {
        logger.info("Fetching customer with id: {}", id);
        try (
            Connection connection = getConnection();
            var stmt = connection.prepareStatement(readQuery + " WHERE id = ?")
        ) {
            stmt.setInt(1, id);
            try (var res = stmt.executeQuery()) {

                if (res.next()) {
                    return Optional.ofNullable(fromResultSet(res));
                }

                logger.warn("No employee found with id: {}", id);
                return Optional.empty();

            }
        }
    }


    @Override
    public List<Customer> getAll(Pagination pagination) throws SQLException {
        Pagination safePagination = (pagination != null) ? pagination : new Pagination(0, 10);

        String query = readQuery + " LIMIT ? OFFSET ?";
        List<Customer> users = new ArrayList<>();

        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, safePagination.perPage());
            stmt.setInt(2, safePagination.perPage() * safePagination.page());

            try (var res = stmt.executeQuery()) {
                while (res.next()) {
                    users.add(fromResultSet(res));
                }
            }
        }
        return users;
    }

    @Override
    public Customer fromResultSet(ResultSet set) throws SQLException {
        return new Customer(
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
    public void update(Customer user) throws SQLException {
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(updateQuery)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getPassword());
            stmt.setInt(6, user.getRoleId());
            stmt.setInt(7, user.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(deleteQuery)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}



