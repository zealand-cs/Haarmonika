package dk.haarmonika.haarmonika.backend.db.daos.booking;

import dk.haarmonika.haarmonika.backend.db.Pagination;
import dk.haarmonika.haarmonika.backend.db.entities.Booking;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BookingDao implements IBookingDao {
    @Override
    public List<Booking> getAll(Pagination pagination) throws SQLException {
        return List.of();
    }

    @Override
    public void save(Booking customer) throws SQLException {

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
