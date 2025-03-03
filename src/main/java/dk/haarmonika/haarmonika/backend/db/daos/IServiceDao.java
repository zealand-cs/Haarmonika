package dk.haarmonika.haarmonika.backend.db.daos;

import dk.haarmonika.haarmonika.backend.db.Pagination;
import dk.haarmonika.haarmonika.backend.db.entities.Service;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface IServiceDao {
    List<Service> getAll(Pagination pagination) throws SQLException;
    void save(Service service) throws SQLException;
    Optional<Service> get(int id) throws SQLException;
    void update(Service service) throws SQLException;
    void delete(int id) throws SQLException;
}

