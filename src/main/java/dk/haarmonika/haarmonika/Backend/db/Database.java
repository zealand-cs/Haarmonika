package dk.haarmonika.haarmonika.Backend.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;


public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    private static volatile Database instance;
    private static HikariDataSource dataSource;

    //Bruges til test af logging, samt database forbindelse.

    /*public static void main(String[] args) {
        Database db = Database.getInstance();
        try (Connection conn = db.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                logger.info("Database connection successful! ✅");
            } else {
                logger.error("Database connection failed! ❌");
            }
        } catch (SQLException e) {
            logger.error("Error connecting to the database: {}", e.getMessage(), e);
        }
    }*/
    
    /**
    Private constructor initializes a database connection-pool by help of HikariCP and ensures correct logging to make
    sure that the connection is running smoothly and that the application reuses connections instead of creating new ones
    to ensure better efficient run program.
     */
    private Database() {
        logger.info("Initializing database connection pool...");

        HikariConfig config = new HikariConfig();

        //Saved as environmental variables to ensure security and flexibility
        config.setJdbcUrl(System.getenv("DB_URL"));
        config.setUsername(System.getenv("DB_USER"));
        config.setPassword(System.getenv("DB_PASSWORD"));
        config.setMaximumPoolSize(10);

        logger.info("Database URL: {}", config.getJdbcUrl());
        logger.info("Max Pool Size: {}", config.getMaximumPoolSize());

        dataSource = new HikariDataSource(config);
        logger.info("Database connection pool initialized successfully.");
    }

    /**
     Returns instance of databaseclass  but checks and ensures threadsafety by using synchronized, gives better performance
     and ensures the instance is being reused throughout the application
     */

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }


    /**
     Used to get a connection from HikariCP, if successful returns an available connection from the connectionpool
     or creates a new one if possible.
     */
    public Connection getConnection() {
        try {
            logger.info("Retrieving a database connection...");
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Error retrieving a database connection: {}", e.getMessage(), e);
            return null;
        }
    }


    /**
     If dataSource (the connectionpool) is not null it will close the connection pool, but if it is null, then nothing
     happens to it.
     */
    public static void close() {
        if (dataSource != null) {
            logger.info("Closing database connection pool...");
            dataSource.close();
            logger.info("Database connection pool closed.");
        }
    }

}
