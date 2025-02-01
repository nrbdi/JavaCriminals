package controllers;

import controllers.interfaces.IUserController;
import models.User;
import repositories.interfaces.IUserRepository;

import java.util.List;

public class UserController implements IUserController {
    private final IUserRepository userRepository;

    public UserController(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String createUser(String name, String email, String phoneNumber, String password, String role, double cash) {
        User user = new User(name, email, phoneNumber, password, role, cash);
        boolean created = userRepository.createUser(user);
        return created ? "User successfully created!" : "Failed to create user.";
    }

    @Override
    public String updateUser(int id, String name, String email, String phoneNumber, String password, String role, double cash) {
        User user = new User(id, name, email, phoneNumber, password, role, cash);
        boolean updated = userRepository.updateUser(user);
        return updated ? "User successfully updated!" : "Failed to update user.";
    }

    @Override
    public String getUserById(int id) {
        User user = userRepository.getUserById(id);
        return (user != null) ? user.toString() : "User not found.";
    }

    @Override
    public String getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        if (users.isEmpty()) {
            return "No users found.";
        }
        StringBuilder response = new StringBuilder("List of users:\n");
        for (User user : users) {
            response.append(user).append("\n");
        }
        return response.toString();
    }

    @Override
    public String loginUser(String email, String password) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            return "User with the provided email does not exist.";
        }
        if (!user.getPassword().equals(password)) {
            return "Incorrect password.";
        }
        return "Login successful! " + user.getId() + ", " + user.getCash();
    }

    @Override
    public boolean updateUserBalance(int userId, double newBalance) {
        return userRepository.updateUserBalance(userId, newBalance);
    }
}
