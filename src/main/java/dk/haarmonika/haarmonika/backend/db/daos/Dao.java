package dk.haarmonika.haarmonika.backend.db.daos;

import dk.haarmonika.haarmonika.backend.db.database.DatabaseConnectionManager;
import dk.haarmonika.haarmonika.backend.db.entities.IEntity;

import java.sql.Connection;

/**
 * Abstract DAO class to implement in DAOs
 * It simply contains a DB connection
 * @param <T> the type to return from the DAO
 */
public abstract class Dao<T extends IEntity> implements IDao<T> {
    protected Connection getConnection() {
        return DatabaseConnectionManager.getConnection();
    }
}