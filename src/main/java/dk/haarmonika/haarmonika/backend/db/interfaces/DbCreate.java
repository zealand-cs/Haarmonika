package dk.haarmonika.haarmonika.backend.db.interfaces;

import java.sql.SQLException;

public interface DbCreate<T> {
    /**
     * Insert a DAO in the database
     * @return the id of the inserted row
     * @throws SQLException
     */
    void save(T entity) throws SQLException;
}
