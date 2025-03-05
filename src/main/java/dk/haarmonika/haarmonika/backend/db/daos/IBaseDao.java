package dk.haarmonika.haarmonika.backend.db.daos;

import dk.haarmonika.haarmonika.backend.db.Pagination;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Base Dao interface for easier implementation in specific Dao interfaces
 * @param <T> the type to work with
 */
public interface IBaseDao<T> {
    /**
     * Gets all items of a certain type
     * @param pagination the limit and offset of returned items
     * @return a List<T> of all items specified
     * @throws SQLException
     */
    List<T> getAll(Pagination pagination) throws SQLException;

    /**
     * Saves an entity to a persistent storage
     * @param entity the entity to store
     * @throws SQLException
     */
    void save(T entity) throws SQLException;

    /**
     * Gets a single item from the persistent storage
     * @param id the id of the item
     * @return an empty Optional if no item with id is found, otherwise Some with the item
     * @throws SQLException
     */
    Optional<T> get(int id) throws SQLException;

    /**
     * Updates an item in the persistent storage with the new values
     * @param entity the entity to use as an update
     * @throws SQLException
     */
    void update(T entity) throws SQLException;

    /**
     * Delete a specific item
     * @param id the id of the item to delete
     * @throws SQLException
     */
    void delete(int id) throws SQLException;
}
