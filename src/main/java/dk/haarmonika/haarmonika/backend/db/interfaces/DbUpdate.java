package dk.haarmonika.haarmonika.backend.db.interfaces;

import dk.haarmonika.haarmonika.backend.db.entities.IEntity;

import java.sql.SQLException;

public interface DbUpdate<T extends IEntity> {
    /**
     * Updates an item in the persistent storage with the new values
     * @param entity the entity to use as an update
     * @throws SQLException
     */
    void update(T entity) throws SQLException;
}
