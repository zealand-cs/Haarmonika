package dk.haarmonika.haarmonika.backend.services;

import dk.haarmonika.haarmonika.backend.db.daos.booking.IBookingDao;
import dk.haarmonika.haarmonika.backend.db.entities.Booking;
import dk.haarmonika.haarmonika.backend.db.validation.BookingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements IBookingService{

    private final IBookingDao bookingDao;
    private final BookingValidator bookingValidator;

    @Autowired
    public BookingService(@Qualifier("bookingDao") IBookingDao bookingDao, BookingValidator bookingValidator) {
        this.bookingDao = bookingDao;
        this.bookingValidator = bookingValidator;
    }

    @Override
    public List<Booking> getAllBookings() throws SQLException {
        return bookingDao.getAll(null);
    }

    @Override
    public void createBooking(Booking booking) throws SQLException {
        bookingValidator.validate(booking);
        bookingDao.save(booking);
    }

    @Override
    public Optional<Booking> getBookingById(int id) throws SQLException {
        return bookingDao.get(id);
    }

    @Override
    public void updateBooking(Booking booking) throws SQLException {
        bookingValidator.validate(booking);
        bookingDao.update(booking);
    }

    @Override
    public void delete(int id) throws SQLException {
        bookingValidator.validateId(id);
        bookingDao.delete(id);
    }
}
