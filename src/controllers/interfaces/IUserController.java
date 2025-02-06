package controllers.interfaces;

import models.User;

public interface IUserController {
    String createUser(String name, String email, String phoneNumber, String password, String role, double cash);
    String updateUser(int id, String name, String email, String phoneNumber, String password, String role, double cash);
    User getUserById(int id);
    String getAllUsers();
    User loginUser(String email, String password);
    boolean updateUserBalance(int userId, double newBalance);
}
