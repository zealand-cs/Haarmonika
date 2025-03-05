package dk.haarmonika.haarmonika.backend.db.interfaces;

import dk.haarmonika.haarmonika.backend.db.entities.IEntity;

import java.sql.SQLException;

public interface DbDelete<T extends IEntity> {
    /**
     * Delete a specific item
     * @param entity the entity to delete
     * @throws SQLException
     */
    default void delete(T entity) throws SQLException {
        delete(entity.getId());
    }

    /**
     * Delete a specific item
     * @param id the id of the item to delete
     * @throws SQLException
     */
    void delete(int id) throws SQLException;
}
