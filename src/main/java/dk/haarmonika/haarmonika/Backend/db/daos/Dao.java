package dk.haarmonika.haarmonika.Backend.db.daos;

import dk.haarmonika.haarmonika.Backend.db.interfaces.DbCreate;
import dk.haarmonika.haarmonika.Backend.db.interfaces.DbDelete;
import dk.haarmonika.haarmonika.Backend.db.interfaces.DbRead;
import dk.haarmonika.haarmonika.Backend.db.interfaces.DbUpdate;

import java.sql.Connection;

/**
 * Abstract DAO class to implement in DAOs
 * It simply contains a DB connection
 * @param <T> the type to return from the DAO
 */
public abstract class Dao<T> implements DbCreate<T>, DbRead<T>, DbUpdate<T>, DbDelete<T> {
    protected final Connection connection;

    Dao(Connection connection) {
        this.connection = connection;
    }
}