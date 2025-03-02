package dk.haarmonika.haarmonika.backend.db.entities;

public class Employee extends User {
    public Employee(int id, String firstname, String lastName, String email, String phone, String password, int roleId) {
        // TODO find role id
        super(id, firstname, lastName, email, phone, password, roleId);
    }

    public Employee(String firstname, String lastName, String email, String phone, String password, int roleId) {
        // TODO find role id
        super(null, firstname, lastName, email, phone, password, roleId);
    }
    public Employee(String firstname, String lastName, String email, String phone, String password) {
        super(null, firstname, lastName, email, phone, password, 100);
    }
}
