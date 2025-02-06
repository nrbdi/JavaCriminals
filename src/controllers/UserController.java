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
    public User loginUser(String email, String password) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            System.out.println("User with the provided email does not exist.");
            return null;
        }

        if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password.");
            return null;
        }

        return user;
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
    public User getUserById(int id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            System.out.println("User not found.");
            return null;
        }
        return user;
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
    public boolean updateUserBalance(int userId, double newBalance) {
        return userRepository.updateUserBalance(userId, newBalance);
    }
}
