package dk.haarmonika.haarmonika.backend.db.daos.booking;

import dk.haarmonika.haarmonika.backend.db.Pagination;
import dk.haarmonika.haarmonika.backend.db.daos.Dao;
import dk.haarmonika.haarmonika.backend.db.entities.Booking;
import dk.haarmonika.haarmonika.backend.db.entities.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Data Access Object (DAO) for håndtering af Booking entiteter i databasen.
 * Denne klasse indeholder metoder til at oprette, hente, opdatere og slette bookings.
 */
@Repository
public class BookingDao extends Dao<Booking> implements IBookingDao {
    private static final Logger logger = LoggerFactory.getLogger(BookingDao.class);

    static final String insertBooking = "INSERT INTO booking (employeeId, customerId, date, cancelled) VALUES (?, ?, ?, ?)";
    static final String insertBookingService = "INSERT INTO bookingService (bookingId, serviceId) VALUES (?, ?)";

    static final String readBooking = "SELECT id, employeeId, customerId, date, cancelled FROM booking";
    static final String readBookingServices = "SELECT s.id, s.name, s.duration FROM service s LEFT JOIN bookingService bs ON bs.serviceId = s.id WHERE bs.bookingId = ?";

    static final String updateBooking = "UPDATE booking SET employeeId = ?, customerId = ?, date = ? WHERE id = ?";
    static final String deleteBookingServices = "DELETE FROM bookingService WHERE bookingId = ?";

    static final String deleteBooking = "UPDATE booking SET cancelled = true WHERE id = ?";

    /**
     * Gemmer en ny booking i databasen.
     *
     * @param booking Booking objektet, der skal gemmes.
     * @throws SQLException Hvis der opstår en SQL-fejl under gemning.
     */
    @Override
    public void save(Booking booking) throws SQLException {
        logger.info("Saving booking: {}", booking);
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(insertBooking);
             var bookingServiceStmt = connection.prepareStatement(insertBookingService)
        ) {
            connection.setAutoCommit(false);
            stmt.setInt(1, booking.getEmployeeId());
            stmt.setInt(2, booking.getCustomerId());
            stmt.setInt(3, booking.getDate());
            stmt.setBoolean(4, booking.isCancelled());
            int rowsAffected = stmt.executeUpdate();

            for (var service : booking.getServices()) {
                bookingServiceStmt.setInt(1, booking.getId());
                bookingServiceStmt.setInt(2, service.getId());
                bookingServiceStmt.executeUpdate();
            }

            connection.commit();
            connection.setAutoCommit(true);

            logger.info("Booking creation Successful, rows affected: {}", rowsAffected);
        }
    }

    /**
     * Henter en booking fra databasen baseret på dens ID.
     *
     * @param id ID'et på den booking, der skal hentes.
     * @return Et Optional objekt, der indeholder bookingen, hvis den findes, ellers et tomt Optional objekt.
     * @throws SQLException Hvis der opstår en SQL-fejl under hentning.
     */
    @Override
    public Optional<Booking> get(int id) throws SQLException {
        logger.info("Fetching booking with id: {}", id);
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(readBooking + " WHERE id = ?");
             var serviceStmt = connection.prepareStatement(readBookingServices);
        ) {
            Booking booking;

            stmt.setInt(1, id);
            var res = stmt.executeQuery();
            if (res.next()) {
                booking = fromResultSet(res);
            } else {
                return Optional.empty();
            }

            serviceStmt.setInt(1, id);
            try (var serviceRes = stmt.executeQuery()) {
                while (serviceRes.next()) {
                    var service = new Service(serviceRes.getInt("s.id"), serviceRes.getString("s.name"), serviceRes.getInt("s.duration"));
                    booking.addService(service);
                }
            }

            logger.warn("No booking found with id: {}", id);
            return Optional.empty();
        }
    }

    /**
     * Henter alle bookings fra databasen med paginering.
     *
     * @param pagination Objekt, der indeholder pagineringsinformation.
     * @return En liste af Booking objekter.
     * @throws SQLException Hvis der opstår en SQL-fejl under hentning.
     */
    @Override
    public List<Booking> getAll(Pagination pagination) throws SQLException {
        Pagination safePagination = (pagination != null) ? pagination : new Pagination(0, 10);

        String query = readBooking + " LIMIT ? OFFSET ?";
        List<Booking> bookings = new ArrayList<>();

        try (
            Connection connection = getConnection();
            var stmt = connection.prepareStatement(query);
            var serviceStmt = connection.prepareStatement(readBookingServices);
        ) {
            connection.setAutoCommit(false);
            stmt.setInt(1, safePagination.perPage());
            stmt.setInt(2, safePagination.perPage() * safePagination.page());

            var res = stmt.executeQuery();

            while (res.next()) {
                var booking = fromResultSet(res);

                serviceStmt.setInt(1, booking.getId());
                var serviceRes = serviceStmt.executeQuery();
                while (serviceRes.next()) {
                    var service = new Service(serviceRes.getInt("s.id"), serviceRes.getString("s.name"), serviceRes.getInt("s.duration"));
                    booking.addService(service);
                }

                bookings.add(booking);
            }

            connection.commit();
            connection.setAutoCommit(true);
            return bookings;
        }
    }
    /**
     * Konverterer et ResultSet objekt til et Booking objekt.
     *
     * @param set ResultSet objektet, der indeholder bookingdata.
     * @return Et Booking objekt.
     * @throws SQLException Hvis der opstår en SQL-fejl under konvertering.
     */
    @Override
    public Booking fromResultSet(ResultSet set) throws SQLException {
        return new Booking(
                set.getInt("id"),
                set.getInt("employeeId"),
                set.getInt("customerId"),
                set.getInt("date"),
                set.getBoolean("cancelled")
        );
    }
    /**
     * Opdaterer en eksisterende booking i databasen.
     *
     * @param booking Booking objektet, der skal opdateres.
     * @throws SQLException Hvis der opstår en SQL-fejl under opdatering.
     */
    @Override
    public void update(Booking booking) throws SQLException {
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(updateBooking);
             var deleteStmt = connection.prepareStatement(deleteBookingServices)
        ) {
            connection.setAutoCommit(false);
            stmt.setInt(1, booking.getEmployeeId());
            stmt.setInt(2, booking.getCustomerId());
            stmt.setInt(3, booking.getDate());
            stmt.setInt(4, booking.getId());
            stmt.executeUpdate();

            deleteStmt.setInt(1, booking.getId());
            deleteStmt.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        }
    }
    /**
     * Sletter en booking fra databasen baseret på dens ID.
     *
     * @param id ID'et på den booking, der skal slettes.
     * @throws SQLException Hvis der opstår en SQL-fejl under sletning.
     */
    @Override
    public void delete(int id) throws SQLException {
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(deleteBooking)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    /**
    * Henter bookings inden for et givent datointerval.
    *
    *@param startDate Startdatoen for intervallet.
    * @param endDate   Slutdatoen for intervallet.
    * @return En liste af Booking objekter inden for det givne interval.
    */
    public List<Booking> getBookingsBetween(LocalDate startDate, LocalDate endDate) throws SQLException {
        String query = "SELECT * FROM booking b JOIN bookingservice bs ON b.id = bs.bookingId JOIN service s ON bs.serviceId = s.id WHERE b.date BETWEEN ? AND ?";
        List<Booking> bookings = new ArrayList<>();

        try (
                Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(query);
        ) {
            stmt.setDate(1, java.sql.Date.valueOf(startDate));
            stmt.setDate(2, java.sql.Date.valueOf(endDate));

            try (ResultSet res = stmt.executeQuery()) {
                while (res.next()) {
                    bookings.add(fromResultSet(res));
                }
            }
        }

        return bookings;
    }
}
