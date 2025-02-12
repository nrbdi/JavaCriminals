package controllers;

import controllers.interfaces.IUserController;
import models.User;
import models.UserFactory;
import repositories.interfaces.IUserRepository;
import utils.Validator;

import java.util.List;

public class UserController implements IUserController {
    private final IUserRepository userRepository;

    public UserController(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loginUser(String email, String password) {
        if (!Validator.isEmailValid(email)) {
            System.out.println("Error: Invalid email format.");
            return null;
        }

        if (!Validator.isPasswordValid(password)) {
            System.out.println("Error: Password must be at least 6 characters long.");
            return null;
        }

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
        if (!Validator.isNonEmpty(name)) {
            return "Error: Name cannot be empty.";
        }
        if (!Validator.isEmailValid(email)) {
            return "Error: Invalid email format.";
        }
        if (!Validator.isPasswordValid(password)) {
            return "Error: Password must be at least 6 characters long.";
        }
        if (!Validator.isNonEmpty(phoneNumber)) {
            return "Error: Phone number cannot be empty.";
        }
        if (!Validator.isNonEmpty(role)) {
            return "Error: Role cannot be empty.";
        }
        if (!Validator.isPositiveNumber(cash)) {
            return "Error: Cash amount cannot be negative.";
        }

        User user = UserFactory.createUser(name, email, phoneNumber, password, role, cash);
        boolean created = userRepository.createUser(user);
        return created ? "User successfully created!" : "Failed to create user.";
    }

    @Override
    public User getUserById(int userId) {
        if (!Validator.isPositiveInteger(userId)) {
            System.out.println("Error: User ID must be a positive number.");
            return null;
        }

        return userRepository.getUserById(userId);
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
        if (!Validator.isPositiveInteger(userId)) {
            System.out.println("Error: User ID must be a positive number.");
            return false;
        }
        if (!Validator.isPositiveNumber(newBalance)) {
            System.out.println("Error: Balance must be a positive number.");
            return false;
        }
        return userRepository.updateUserBalance(userId, newBalance);
    }
}
