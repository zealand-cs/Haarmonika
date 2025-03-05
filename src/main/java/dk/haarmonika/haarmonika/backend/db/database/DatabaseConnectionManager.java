package dk.haarmonika.haarmonika.backend.db.database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 Manages database connections.
 */
public class DatabaseConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionManager.class);
    private static final DatabaseConnectionPool pool = DatabaseConnectionPool.getInstance();

    public static Connection getConnection() {
        try {
            logger.info("Retrieving a database connection...");
            Connection connection = pool.getDataSource().getConnection();
            logger.info("Database connection successfully retrieved.");
            return connection;
        } catch (SQLException e) {
            logger.error("Error retrieving a database connection: {}", e.getMessage(), e);
            return null;
        }
    }

    public static void close() {
        if (pool.getDataSource() != null) {
            logger.info("Closing database connection pool...");
            pool.getDataSource().close();
            logger.info("Database connection pool closed.");
        }
    }

}
