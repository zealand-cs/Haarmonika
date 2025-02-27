package dk.haarmonika.haarmonika.Backend.db.entities;

public class Employee extends User {
    public Employee(int id, String firstname, String lastName, String email, String phone, String password) {
        // TODO find role id
        super(id, firstname, lastName, email, phone, password, 100);
    }
}
