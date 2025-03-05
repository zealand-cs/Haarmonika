package dk.haarmonika.haarmonika.backend.db.daos;

import dk.haarmonika.haarmonika.backend.db.database.DatabaseConnectionManager;
import dk.haarmonika.haarmonika.backend.db.interfaces.DbCreate;
import dk.haarmonika.haarmonika.backend.db.interfaces.DbDelete;
import dk.haarmonika.haarmonika.backend.db.interfaces.DbRead;
import dk.haarmonika.haarmonika.backend.db.interfaces.DbUpdate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Abstract DAO class to implement in DAOs
 * It simply contains a DB connection
 * @param <T> the type to return from the DAO
 */
public abstract class Dao<T> implements DbCreate<T>, DbRead<T>, DbUpdate<T>, DbDelete<T> {
    protected Connection getConnection() throws SQLException {
        return DatabaseConnectionManager.getConnection();
    }
}