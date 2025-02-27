package dk.haarmonika.haarmonika.backend.db.interfaces;

import java.sql.SQLException;

public interface DbUpdate<T> {
    void update(T dao) throws SQLException;
}
