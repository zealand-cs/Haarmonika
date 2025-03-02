package dk.haarmonika.haarmonika.backend.db.entities;

public class Customer extends User {
    public Customer(int id, String firstname, String lastName, String email, String phone, String password, int roleId) {
        // TODO find role id
        super(id, firstname, lastName, email, phone, password, roleId);
    }

    public Customer(String firstname, String lastName, String email, String phone, String password, int roleId) {
        // TODO find role id
        super(null, firstname, lastName, email, phone, password, roleId);
    }

    public Customer(String firstname, String lastName, String email, String phone, String password) {
        // TODO find role id
        super(null, firstname, lastName, email, phone, password, 50);
    }
}
