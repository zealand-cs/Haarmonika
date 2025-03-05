package dk.haarmonika.haarmonika.backend.db.daos.customer;

import dk.haarmonika.haarmonika.backend.db.daos.IDao;
import dk.haarmonika.haarmonika.backend.db.entities.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerDao extends IDao<Customer> { }

