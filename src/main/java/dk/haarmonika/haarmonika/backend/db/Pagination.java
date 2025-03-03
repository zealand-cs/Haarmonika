package dk.haarmonika.haarmonika.backend.db;


public record Pagination(int page, int perPage) {
    public Pagination {
        if (page < 0) throw new IllegalArgumentException("Page number cannot be negative");
        if (perPage <= 0) throw new IllegalArgumentException("Per page must be greater than zero");
    }
}
