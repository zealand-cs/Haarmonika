package dk.haarmonika.haarmonika.backend.services;

import dk.haarmonika.haarmonika.backend.db.entities.Booking;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface IBookingService {

    List<Booking> getAllBookings() throws SQLException;
    void createBooking(Booking booking) throws SQLException;
    Optional<Booking> getBookingById(int id) throws SQLException;
    void updateBooking(Booking booking) throws SQLException;
    void delete(int id) throws SQLException;
    List<Booking> getBookingsBetween(LocalDate startDate, LocalDate endDate) throws SQLException;
}
