/*

package dk.haarmonika.haarmonika.backend.services;

import dk.haarmonika.haarmonika.backend.db.daos.IServiceDao;
import dk.haarmonika.haarmonika.backend.db.daos.ServiceDao;
import dk.haarmonika.haarmonika.backend.db.entities.Service;
import dk.haarmonika.haarmonika.backend.db.validation.ServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceService implements IServiceService {
    private final IServiceDao serviceDao;
    private final ServiceValidator serviceValidator;

    @Autowired
    public ServiceService(@Qualifier("serviceDao") IServiceDao serviceDao, ServiceValidator serviceValidator)  {
        this.serviceDao = serviceDao;
        this.serviceValidator = serviceValidator;
    }

    @Override
    public List<Service> getAllServices() throws SQLException {
        return serviceDao.getAll(null);
    }

    @Override
    public void createService(Service service) throws SQLException {
        serviceValidator.validate(service);
        serviceDao.save(service);
    }

    @Override
    public Optional<Service> getServiceById(int id) throws SQLException{
        return serviceDao.get(id);
    }

    @Override
    public void updateService(Service service) throws SQLException {
        serviceValidator.validate(service);
        serviceDao.update(service);
    }

    @Override
    public void delete (int id) throws SQLException {
        serviceValidator.validateId(id);
        serviceDao.delete(id);
    }

}

*/
