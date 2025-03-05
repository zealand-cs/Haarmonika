package dk.haarmonika.haarmonika.backend.db.interfaces;

import dk.haarmonika.haarmonika.backend.db.Pagination;
import dk.haarmonika.haarmonika.backend.db.entities.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DbRead<T extends IEntity> {
    /**
     * Gets a single item from the persistent storage
     * @param id the id of the item
     * @return an empty Optional if no item with id is found, otherwise Some with the item
     * @throws SQLException
     */
    Optional<T> get(int id) throws SQLException;

    /**
     * Gets all items of a certain type
     * @param pagination the limit and offset of returned items
     * @return a List<T> of all items specified
     * @throws SQLException
     */
    List<T> getAll(Pagination pagination) throws SQLException;
    T fromResultSet(ResultSet set) throws SQLException;
}
