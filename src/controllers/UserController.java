package controllers;

import controllers.interfaces.IUserController;
import models.User;
import repositories.interfaces.IUserRepository;

import java.util.List;

public class UserController implements IUserController {
    private final IUserRepository repo;
    public UserController(IUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createUser(String name, String email, String phoneNumber, String password, String role) {
        User user = new User(name, email, phoneNumber, password, role);

        boolean created = repo.createUser(user);
        return (created) ? "User was created" : "User creation failed";
    }

    @Override
    public String updateUser(String name, String email, String phoneNumber, String password, String role) {
        User user = new User(name, email, phoneNumber, password, role);

        boolean updated = repo.updateUser(user);
        return (updated) ? "User was successfully updated" : "User update failed";
    }


    @Override
    public String getUserById(int id) {
        User user = repo.getUserById(id);
        return (user == null) ? "User was not found" : user.toString();
    }

    @Override
    public String loginUser(String email, String password) {
        User user = repo.getUserByEmail(email); // Запрос пользователя из репозитория по email
        if (user == null) {
            return "User with the provided email does not exist.";
        }
        if (!user.getPassword().equals(password)) {
            return "Incorrect password.";
        }
        return "Login successful! Welcome, " + user.getName() + ".";
    }


    @Override
    public String getAllUsers() {
        List<User> users = repo.getAllUsers();
        StringBuilder response = new StringBuilder();
        for (User user : users) {
            response.append(user.toString()).append("\n");
        }
        return response.toString();
    }
}
