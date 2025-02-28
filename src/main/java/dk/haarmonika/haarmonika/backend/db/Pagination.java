package dk.haarmonika.haarmonika.backend.db;

//Burde vel ændres til Record som der også bliver foreslået?
public class Pagination {

    public final int page;
    public final int perPage;



    public Pagination(int page, int perPage) {
        if (page < 0) throw new IllegalArgumentException("Page number cannot be negative");
        if (perPage <= 0) throw new IllegalArgumentException("Per page must be greater than zero");

        this.page = page;
        this.perPage = perPage;

    }
}
