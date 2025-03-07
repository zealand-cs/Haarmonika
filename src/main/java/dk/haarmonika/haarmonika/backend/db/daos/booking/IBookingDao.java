package dk.haarmonika.haarmonika.backend.db.daos.booking;

import dk.haarmonika.haarmonika.backend.db.daos.IDao;
import dk.haarmonika.haarmonika.backend.db.entities.Booking;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface IBookingDao extends IDao<Booking> {

    List<Booking> getBookingsBetween(LocalDate startDate, LocalDate endDate) throws SQLException;

}
