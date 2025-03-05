package dk.haarmonika.haarmonika.backend.db.entities;

import java.util.List;

public class Booking implements IEntity {
    private Integer id;
    private Employee employee;
    private Customer customer;
    private List<Service> services;
    private boolean cancelled;

    @Override
    public Integer getId() {
        return id;
    }
}
