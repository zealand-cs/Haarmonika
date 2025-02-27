package dk.haarmonika.haarmonika.backend.db.Database;

import com.zaxxer.hikari.HikariConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 Handles database configuration settings.
 */
public class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    private static final HikariConfig config = new HikariConfig();

    static {
        logger.info("Loading database configuration...");
        config.setJdbcUrl(System.getenv("DB_URL"));
        config.setUsername(System.getenv("DB_USER"));
        config.setPassword(System.getenv("DB_PASSWORD"));
        config.setMaximumPoolSize(10);
        logger.info("Database configuration loaded.");
    }

    public static HikariConfig getConfig() {
        return config;
    }
}