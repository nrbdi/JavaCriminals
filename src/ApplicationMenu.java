import controllers.AdministrationController;
import controllers.UserController;
import controllers.VehicleController;
import models.User;
import utils.Validator;

import java.util.Scanner;

public class ApplicationMenu {
    private final UserController userController;
    private final VehicleController vehicleController;
    private final AdministrationController adminController;
    private MyApplication_2 adminApp;
    private final Scanner scanner;
    private User currentUser;

    public ApplicationMenu(UserController userController, VehicleController vehicleController, AdministrationController adminController) {
        this.userController = userController;
        this.vehicleController = vehicleController;
        this.adminController = adminController;
        this.adminApp = new MyApplication_2(adminController, vehicleController, userController);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n1. Add new user");
            System.out.println("2. Update existing user");
            System.out.println("3. Login");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();
            if (!input.matches("\\d+")) {
                System.out.println("Error: Please enter a valid number.");
                continue;
            }

            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1 -> addUser();
                case 2 -> updateUser();
                case 3 -> loginAndRedirect();
                case 0 -> {
                    System.out.println("Exiting application. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please choose again.");
            }
        }
    }

    private void addUser() {
        String name, email, phoneNumber, password, role;
        double cash;

        do {
            System.out.println("Enter name:");
            name = scanner.nextLine();
            if (!Validator.isNonEmpty(name)) {
                System.out.println("Error: Name cannot be empty!");
            }
        } while (!Validator.isNonEmpty(name));

        do {
            System.out.println("Enter email:");
            email = scanner.nextLine();
            if (!Validator.isEmailValid(email)) {
                System.out.println("Error: Invalid email format! Please try again.");
            }
        } while (!Validator.isEmailValid(email));

        do {
            System.out.println("Enter phone number:");
            phoneNumber = scanner.nextLine();
            if (!Validator.isNonEmpty(phoneNumber)) {
                System.out.println("Error: Phone number cannot be empty!");
            }
        } while (!Validator.isNonEmpty(phoneNumber));

        do {
            System.out.println("Enter password:");
            password = scanner.nextLine();
            if (!Validator.isPasswordValid(password)) {
                System.out.println("Error: Password must be at least 6 characters long!");
            }
        } while (!Validator.isPasswordValid(password));

        do {
            System.out.println("Enter role (user/admin/manager):");
            role = scanner.nextLine();
            if (!Validator.isNonEmpty(role)) {
                System.out.println("Error: Role cannot be empty!");
            }
        } while (!Validator.isNonEmpty(role));

        do {
            System.out.println("Enter cash amount:");
            cash = scanner.nextDouble();
            scanner.nextLine();
            if (!Validator.isPositiveNumber(cash)) {
                System.out.println("Error: Cash cannot be negative!");
            }
        } while (!Validator.isPositiveNumber(cash));

        String response = userController.createUser(name, email, phoneNumber, password, role, cash);
        System.out.println(response);
    }

    private void updateUser() {
        System.out.println("Enter user ID:");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (!Validator.isPositiveInteger(id)) {
            System.out.println("Error: User ID must be a positive number!");
            return;
        }

        String name, email, phoneNumber, password, role;
        double cash;

        do {
            System.out.println("Enter new name:");
            name = scanner.nextLine();
            if (!Validator.isNonEmpty(name)) {
                System.out.println("Error: Name cannot be empty!");
            }
        } while (!Validator.isNonEmpty(name));

        do {
            System.out.println("Enter new email:");
            email = scanner.nextLine();
            if (!Validator.isEmailValid(email)) {
                System.out.println("Error: Invalid email format!");
            }
        } while (!Validator.isEmailValid(email));

        do {
            System.out.println("Enter new phone number:");
            phoneNumber = scanner.nextLine();
            if (!Validator.isNonEmpty(phoneNumber)) {
                System.out.println("Error: Phone number cannot be empty!");
            }
        } while (!Validator.isNonEmpty(phoneNumber));

        do {
            System.out.println("Enter new password:");
            password = scanner.nextLine();
            if (!Validator.isPasswordValid(password)) {
                System.out.println("Error: Password must be at least 6 characters long!");
            }
        } while (!Validator.isPasswordValid(password));

        do {
            System.out.println("Enter new role (user/admin/manager):");
            role = scanner.nextLine();
            if (!Validator.isNonEmpty(role)) {
                System.out.println("Error: Role cannot be empty!");
            }
        } while (!Validator.isNonEmpty(role));

        do {
            System.out.println("Enter new cash amount:");
            cash = scanner.nextDouble();
            scanner.nextLine();
            if (!Validator.isPositiveNumber(cash)) {
                System.out.println("Error: Cash cannot be negative!");
            }
        } while (!Validator.isPositiveNumber(cash));

        String response = userController.updateUser(id, name, email, phoneNumber, password, role, cash);
        System.out.println(response);
    }

    private void loginAndRedirect() {
        String email, password;

        do {
            System.out.println("Enter email:");
            email = scanner.nextLine();
            if (!Validator.isEmailValid(email)) {
                System.out.println("Error: Invalid email format! Please try again.");
            }
        } while (!Validator.isEmailValid(email));

        do {
            System.out.println("Enter password:");
            password = scanner.nextLine();
            if (!Validator.isPasswordValid(password)) {
                System.out.println("Error: Password must be at least 6 characters long!");
            }
        } while (!Validator.isPasswordValid(password));

        User user = userController.loginUser(email, password);
        if (user == null) {
            System.out.println("Login failed. Please check your credentials.");
            return;
        }

        this.currentUser = user;
        System.out.printf("Login successful! User ID: %d, Role: %s, Balance: %.2f%n",
                user.getId(), user.getRole(), user.getCash());

        if (user.getRole().equalsIgnoreCase("user")) {
            MyApplication userApp = new MyApplication(vehicleController, currentUser);
            userApp.start();
        } else {
            adminApp.start(user.getRole());
        }
    }

}
