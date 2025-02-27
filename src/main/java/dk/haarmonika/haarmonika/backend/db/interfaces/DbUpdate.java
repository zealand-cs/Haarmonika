package dk.haarmonika.haarmonika.backend.db.interfaces;

import java.sql.SQLException;

public interface DbUpdate<T> {
    void update(T entity) throws SQLException;
}
