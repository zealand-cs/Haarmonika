package dk.haarmonika.haarmonika.backend.db.daos;

import dk.haarmonika.haarmonika.backend.db.entities.IEntity;
import dk.haarmonika.haarmonika.backend.db.interfaces.DbCreate;
import dk.haarmonika.haarmonika.backend.db.interfaces.DbDelete;
import dk.haarmonika.haarmonika.backend.db.interfaces.DbRead;
import dk.haarmonika.haarmonika.backend.db.interfaces.DbUpdate;

/**
 * Dao interface for easier implementation in specific Dao interfaces
 * @param <T> the type to work with
 */
public interface IDao<T extends IEntity> extends DbCreate<T>, DbRead<T>, DbUpdate<T>, DbDelete<T> { }
