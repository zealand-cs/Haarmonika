
package dk.haarmonika.haarmonika.backend.db.daos;

import dk.haarmonika.haarmonika.backend.db.Pagination;
import dk.haarmonika.haarmonika.backend.db.entities.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceDao extends Dao<Service> implements IServiceDao{
    private static final Logger logger = LoggerFactory.getLogger(ServiceDao.class);

    static final String createQuery = "INSERT INTO service (name, duration) VALUES (?, ?)";
    static final String readQuery = "SELECT id, name, duration FROM service";
    static final String updateQuery = "UPDATE service SET name = ?, duration = ? WHERE id = ?";
    static final String deleteQuery = "DELETE FROM service WHERE id = ?";

    @Override
    public void save(Service entity) throws SQLException {
        logger.info("Saving service: {}", entity);
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(createQuery)) {
            stmt.setString(1, entity.getName());
            stmt.setInt(2, entity.getDuration());
            int rowsAffected = stmt.executeUpdate();
            logger.info("Service Creation Successful, rows affected: {}", rowsAffected);
        }
    }

    @Override
    public Optional<Service> get(int id) throws SQLException {
        logger.info("Fetching service with id: {}", id);
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(readQuery + " WHERE id = ?")) {
            stmt.setInt(1, id);
            try (var res = stmt.executeQuery()) {
                if (res.next()) {
                    return Optional.ofNullable(fromResultSet(res));
                }
                logger.warn("No service found with id: {}", id);
                return Optional.empty();
            }
        }
    }

    @Override
    public List<Service> getAll(Pagination pagination) throws SQLException {
        Pagination safePagination = (pagination !=null) ? pagination : new Pagination(0, 10);

        String query = readQuery + " LIMIT ? OFFSET ?";
        List<Service> services = new ArrayList<>();

        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, safePagination.perPage());
            stmt.setInt(2, safePagination.perPage() * safePagination.page());

                try (var res = stmt.executeQuery()) {
                    while (res.next()) {
                        services.add(fromResultSet(res));
                    }
                }
        }
        return services;
    }

    @Override
    public Service fromResultSet(ResultSet set) throws SQLException {
        return new Service(
                set.getInt("id"),
                set.getString("name"),
                set.getInt("duration")
        );
    }

    @Override
    public void update(Service entity) throws SQLException {
        try (Connection connection = getConnection();
        var stmt = connection.prepareStatement(updateQuery)) {
            stmt.setString(1, entity.getName());
            stmt.setInt(2, entity.getDuration());
            stmt.setInt(3, entity.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Service entity) throws SQLException {
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(deleteQuery)) {
            stmt.setInt(1, entity.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (Connection connection = getConnection();
             var stmt = connection.prepareStatement(deleteQuery)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }

    }


}

