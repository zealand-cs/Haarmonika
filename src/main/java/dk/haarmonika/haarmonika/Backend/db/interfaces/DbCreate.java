package dk.haarmonika.haarmonika.Backend.db.interfaces;

import java.sql.SQLException;

public interface DbCreate<T> {
    /**
     * Insert a DAO in the database
     * @return the id of the inserted row
     * @throws SQLException
     */
    int save(T dao) throws SQLException;
}
