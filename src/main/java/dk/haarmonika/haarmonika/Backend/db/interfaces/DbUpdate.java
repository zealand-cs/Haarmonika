package dk.haarmonika.haarmonika.Backend.db.interfaces;

import java.sql.SQLException;

public interface DbUpdate<T> {
    void update(T dao) throws SQLException;
}
