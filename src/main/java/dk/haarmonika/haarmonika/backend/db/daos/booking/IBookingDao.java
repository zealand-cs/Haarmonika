package dk.haarmonika.haarmonika.backend.db.daos.booking;

import dk.haarmonika.haarmonika.backend.db.daos.IDao;
import dk.haarmonika.haarmonika.backend.db.entities.Booking;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookingDao extends IDao<Booking> { }
