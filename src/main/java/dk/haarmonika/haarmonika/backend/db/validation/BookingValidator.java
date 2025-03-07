package dk.haarmonika.haarmonika.backend.db.validation;

import dk.haarmonika.haarmonika.backend.db.entities.Booking;

public class BookingValidator extends BaseValidator{

    @Override
    public void validate(Object entity) {
        Booking booking = (Booking) entity;
        if (booking.getEmployeeId() <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }
    }
}
