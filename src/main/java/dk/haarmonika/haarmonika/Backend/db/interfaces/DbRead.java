package dk.haarmonika.haarmonika.Backend.db.interfaces;

import dk.haarmonika.haarmonika.Backend.db.Pagination;

public interface DbRead {
    Object read(String id);
    Object readAll(Pagination pagination);
}
