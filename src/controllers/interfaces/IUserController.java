package controllers.interfaces;

import models.User;

public interface IUserController {
    String createUser(String name, String email, String phoneNumber, String password, String role);
    String updateUser(String name, String email, String phoneNumber, String password, String role);
    String getUserById(int id);
    String loginUser(String email, String password);
    String getAllUsers();
}
