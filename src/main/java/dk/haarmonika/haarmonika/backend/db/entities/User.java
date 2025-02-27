package dk.haarmonika.haarmonika.backend.db.entities;

public abstract class User {
    public Integer id;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    public String password;
    public int roleId;

    public User(Integer id, String firstname, String lastName, String email, String phone, String password, int roleId) {
        this.id = id;
        this.firstName = firstname;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.roleId = roleId;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public int getRoleId() {
        return roleId;
    }
}
