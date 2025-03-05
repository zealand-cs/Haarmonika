package dk.haarmonika.haarmonika.backend.db.daos.booking;

import dk.haarmonika.haarmonika.backend.db.Pagination;
import dk.haarmonika.haarmonika.backend.db.daos.Dao;
import dk.haarmonika.haarmonika.backend.db.daos.customer.CustomerDao;
import dk.haarmonika.haarmonika.backend.db.entities.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class BookingDao extends Dao<Booking> implements IBookingDao {
    private static final Logger logger = LoggerFactory.getLogger(CustomerDao.class);

    static final String insertBooking = "INSERT INTO booking (employeeId, customerId, date, cancelled) VALUES (?, ?, ?, ?)";
    static final String insertBookingService = "INSERT INTO bookingService (bookingId, serviceId) VALUES (?, ?)";

    static final String readBooking = "SELECT id, employeeId, customerId, cate, cancelled FROM booking";
    static final String readBookingServices = "SELECT s.name, s.duration FROM service s LEFT JOIN bookingService bs ON bs.serviceId = s.id WHERE bs.bookingId = ?";

    static final String updateBooking = "UPDATE booking SET employeeId = ?, customerId = ?, date = ?, cancelled = ? WHERE id = ?";

    static final String deleteBooking = "DELETE FROM booking WHERE id = ?";
    static final String deleteBookingServices = "DELETE FROM bookingService WHERE bookingId = ?";

    @Override
    public void save(Booking booking) throws SQLException {
        logger.info("Saving booking: {}", booking);
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(insertBooking)) {
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
    public List<Booking> getAll(Pagination pagination) throws SQLException {
        return List.of();
    }

    @Override
    public Booking fromResultSet(ResultSet set) throws SQLException {
        return null;
    }

    @Override
    public Optional<Booking> get(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public void update(Booking customer) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }
}
