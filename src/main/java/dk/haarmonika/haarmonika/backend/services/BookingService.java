package dk.haarmonika.haarmonika.backend.services;

import dk.haarmonika.haarmonika.backend.db.daos.booking.IBookingDao;
import dk.haarmonika.haarmonika.backend.db.entities.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements IBookingService {
    private final IBookingDao bookingDao;

    @Autowired
    public BookingService(@Qualifier("bookingDao") IBookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @Override
    public List<Booking> getAllBookings() throws SQLException {
        return bookingDao.getAll(null);
    }

    @Override
    public void createBooking(Booking booking) throws SQLException {
        bookingDao.save(booking);
    }

    @Override
    public Optional<Booking> getBookingById(int id) throws SQLException {
        return bookingDao.get(id);
    }

    @Override
    public void updateBooking(Booking booking) throws SQLException {
        bookingDao.update(booking);
    }

    @Override
    public void cancelBooking(int id) throws SQLException {
        bookingDao.delete(id);
    }
}
