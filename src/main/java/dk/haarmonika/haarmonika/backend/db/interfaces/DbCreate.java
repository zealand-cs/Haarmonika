package dk.haarmonika.haarmonika.backend.db.interfaces;

import dk.haarmonika.haarmonika.backend.db.entities.IEntity;

import java.sql.SQLException;

public interface DbCreate<T extends IEntity> {
    /**
     * Saves an entity to a persistent storage
     * @param entity the entity to store
     * @throws SQLException
     */
    void save(T entity) throws SQLException;
}
