package dk.haarmonika.haarmonika.Backend.db.interfaces;

import dk.haarmonika.haarmonika.Backend.db.Pagination;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DbRead<T> {
    Optional<T> get(int id) throws SQLException;
    List<T> getAll(Pagination pagination) throws SQLException;
    T fromResultSet(ResultSet set) throws SQLException;
}
