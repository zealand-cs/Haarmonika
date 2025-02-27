package dk.haarmonika.haarmonika.backend.db.interfaces;

import java.sql.SQLException;

public interface DbDelete<T> {
    /**
     * Delete an object from the database
     */
    void delete(T dao) throws SQLException;
}
