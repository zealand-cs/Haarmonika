/*
package dk.haarmonika.haarmonika.backend.services;

import dk.haarmonika.haarmonika.backend.db.daos.ServiceDao;
import dk.haarmonika.haarmonika.backend.db.entities.Service;

import java.sql.SQLException;
import java.util.List;

public class ServiceService {
    private final ServiceDao serviceDao;

    ServiceService(ServiceDao dao) {
        this.serviceDao = dao;
    }

    public List<Service> getAllServices() throws SQLException {
        return serviceDao.getAll(null);
    }

    public void createService(Service service) throws SQLException {
        serviceDao.save(service);
    }
}
*/
