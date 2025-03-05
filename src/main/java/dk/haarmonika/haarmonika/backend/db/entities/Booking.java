package dk.haarmonika.haarmonika.backend.db.entities;

import java.util.List;

public class Booking implements IEntity {
    private Integer id;
    private int employeeId;
    private int customerId;
    private List<Service> services;
    private boolean cancelled;

    @Override
    public Integer getId() {
        return id;
    }
}
