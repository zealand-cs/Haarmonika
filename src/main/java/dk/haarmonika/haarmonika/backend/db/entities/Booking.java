package dk.haarmonika.haarmonika.backend.db.entities;

import java.util.ArrayList;
import java.util.List;

public class Booking implements IEntity {
    private Integer id;
    private int employeeId;
    private int customerId;
    private List<Service> services = new ArrayList<>();
    private int date;
    private boolean cancelled;

    public Booking(Integer id, int employeeId, int customerId, int date, boolean cancelled) {
        this.id = id;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.date = date;
        this.cancelled = cancelled;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void addService(Service service) {
        services.add(service);
    }

    public void removeService(Service service) {
        services.remove(service);
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
