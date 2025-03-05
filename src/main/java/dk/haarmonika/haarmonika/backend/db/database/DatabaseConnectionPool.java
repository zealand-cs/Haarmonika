package dk.haarmonika.haarmonika.backend.db.database;


import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 Manages the connection pool.
 */

public class DatabaseConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionPool.class);
    private static volatile DatabaseConnectionPool instance;
    private final HikariDataSource dataSource;

    private DatabaseConnectionPool() {
        logger.info("Initializing database connection pool...");
        dataSource = new HikariDataSource(DatabaseConfig.getConfig());
        logger.info("Database connection pool initialized successfully.");
    }

    public static DatabaseConnectionPool getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnectionPool.class) {
                if (instance == null) {
                    instance = new DatabaseConnectionPool();
                }
            }
        }
        return instance;
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }
}
