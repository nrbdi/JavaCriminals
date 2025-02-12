package models;

public class UserFactory {
    public static User createUser(String name, String email, String phoneNumber, String password, String role, double balance) {
        return new User(name, email, phoneNumber, password, role, balance);
    }
}
