package dk.haarmonika.haarmonika.backend.db.daos;

import dk.haarmonika.haarmonika.backend.db.Pagination;
import dk.haarmonika.haarmonika.backend.db.entities.Employee;
import dk.haarmonika.haarmonika.backend.db.entities.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceDao extends Dao<Service> {
    public ServiceDao(Connection conn) {
        super(conn);
    }

    static final String createQuery = "INSERT INTO service (name, duration) VALUES (?, ?)";
    static final String readQuery = "SELECT id, name, duration FROM service";
    static final String updateQuery = "UPDATE service SET name = ?, duration = ? WHERE id = ?";
    static final String deleteQuery = "DELETE FROM service WHERE id = ?";

    @Override
    public void save(Service entity) throws SQLException {
        var stmt = connection.prepareStatement(createQuery);
        stmt.setString(1, entity.getName());
        stmt.setInt(2, entity.getDuration());
        stmt.executeUpdate();
    }

    @Override
    public Optional<Service> get(int id) throws SQLException {
        var stmt = connection.prepareStatement(readQuery + " WHERE id = ?");
        stmt.setInt(1, id);
        var res = stmt.executeQuery();

        Optional<Service> entity = Optional.empty();
        if (res.next()) {
            entity = Optional.ofNullable(fromResultSet(res));
        }

        return entity;
    }

    @Override
    public List<Service> getAll(Pagination pagination) throws SQLException {
        var query = readQuery;
        // Add pagination to query if we want pages
        if (pagination != null) {
            query += " LIMIT " + pagination.perPage + " OFFSET " + pagination.perPage * pagination.page;
        }
        var stmt = connection.prepareStatement(query);
        try (var res = stmt.executeQuery()) {
            List<Service> services = new ArrayList<>();
            while (res.next()) {
                services.add(fromResultSet(res));
            }

            return services;
        }
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
        var stmt = connection.prepareStatement(updateQuery);
        stmt.setString(1, entity.getName());
        stmt.setInt(2, entity.getDuration());
        stmt.setInt(3, entity.getId());
        stmt.executeUpdate();
    }

    @Override
    public void delete(Service dao) throws SQLException {
        connection.prepareStatement(deleteQuery);
    }
}
