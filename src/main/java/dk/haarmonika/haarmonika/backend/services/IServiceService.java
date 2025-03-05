package dk.haarmonika.haarmonika.backend.services;

import dk.haarmonika.haarmonika.backend.db.entities.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IServiceService {
    List<Service> getAllServices() throws SQLException;
    void createService(Service service) throws SQLException;
    Optional<Service> getServiceById(int id) throws SQLException;
    void updateService(Service service) throws SQLException;
    void delete(int id) throws SQLException;
}
