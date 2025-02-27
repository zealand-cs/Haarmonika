package dk.haarmonika.haarmonika.backend.db.entities;

public abstract class User {
    public int id;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    public String password;
    public int roleId;

    public User(int id, String firstname, String lastName, String email, String phone, String password, int roleId) {
        this.id = id;
        this.firstName = firstname;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.roleId = roleId;
    }
}
