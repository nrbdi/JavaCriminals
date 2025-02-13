import controllers.AdministrationController;
import controllers.VehicleController;
import controllers.UserController;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import models.User;
import utils.Validator;

public class MyApplication_2 {
    private final AdministrationController adminController;
    private final VehicleController vehicleController;
    private final UserController userController;
    private final Scanner scanner;

    public MyApplication_2(AdministrationController adminController, VehicleController vehicleController, UserController userController) {
        this.adminController = adminController;
        this.vehicleController = vehicleController;
        this.userController = userController;
        this.scanner = new Scanner(System.in);
    }

    public void start(String role) {
        if ("admin".equalsIgnoreCase(role)) {
            adminMenu();
        } else if ("manager".equalsIgnoreCase(role)) {
            managerMenu();
        } else {
            System.out.println("Access denied. Invalid role.");
        }
    }

    private void adminMenu() {
        while (true) {
            try {
                System.out.println("\nAdmin Menu:");
                System.out.println("1. View all users");
                System.out.println("2. View all administration");
                System.out.println("3. View user by ID");
                System.out.println("4. Delete a user");
                System.out.println("5. Show purchase & reservation report");
                System.out.println("6. Update existing user");
                System.out.println("7. Create admin/manager");
                System.out.println("0. Logout");
                System.out.print("Enter your choice: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Error: Please enter a valid number.");
                    scanner.nextLine();
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> viewAllUsers();
                    case 2 -> viewAllAdministration();
                    case 3 -> viewUserById();
                    case 4 -> deleteUser();
                    case 5 -> showPurchaseReport();
                    case 6 -> updateUser();
                    case 7 -> createAdminOrManager();
                    case 0 -> {
                        System.out.println("Logging out...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please choose again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    private void managerMenu() {
        while (true) {
            try {
                System.out.println("\nManager Menu:");
                System.out.println("1. Add a vehicle");
                System.out.println("2. Delete a vehicle");
                System.out.println("3. View all vehicles");
                System.out.println("4. View vehicle by ID");
                System.out.println("5. Show purchase & reservation report");
                System.out.println("0. Logout");
                System.out.print("Enter your choice: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Error: Please enter a valid number.");
                    scanner.nextLine();
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> addVehicle();
                    case 2 -> deleteVehicle();
                    case 3 -> viewAllVehicles();
                    case 4 -> viewVehicleById();
                    case 5 -> showPurchaseReport();
                    case 0 -> {
                        System.out.println("Logging out...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please choose again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                scanner.nextLine(); // Очистка буфера
            }
        }
    }

    private void showPurchaseReport() {
        System.out.println("\n==========================================================================");
        System.out.println("                    Purchase & Reservation Report                         ");
        System.out.println("==========================================================================");

        System.out.println("+------+----------------------+------------------------------+-------------+-------------+------------+-------------+----------------+");
        System.out.println("| ID   | User Name            | Email                        | Brand       | Model       | Price      | Status      | Purchase Date  |");
        System.out.println("+------+----------------------+------------------------------+-------------+-------------+------------+-------------+----------------+");

        List<String[]> report = adminController.getPurchaseAndReservationReport();

        if (report.isEmpty()) {
            System.out.println("| No purchased or reserved vehicles found.                                                                      |");
        } else {
            for (String[] row : report) {
                System.out.printf("| %-4s | %-20s | %-28s | %-11s | %-11s | $%-9s | %-11s | %-14s |\n",
                        row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]);
            }
        }
        System.out.println("+------+----------------------+------------------------------+-------------+-------------+------------+-------------+----------------+");
    }

    private void viewAllUsers() {
        String users = adminController.getAllUsers();
        System.out.println(users);
    }

    private void viewAllAdministration() {
        String administration = adminController.getAllAdministration();
        System.out.println(administration);
    }


    private void viewUserById() {
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        User user = adminController.getUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
        } else {
            System.out.println("\n=========================");
            System.out.println("       User Details      ");
            System.out.println("=========================");
            System.out.printf("| Name     : %s%n", user.getName());
            System.out.printf("| Email    : %s%n", user.getEmail());
            System.out.printf("| Phone    : %s%n", user.getPhoneNumber());
            System.out.printf("| Role     : %s%n", user.getRole());

            if (user.getRole().equalsIgnoreCase("user")) {
                System.out.printf("| Balance  : %.2f%n", user.getCash());
            }
            System.out.println("=========================\n");
        }
    }

    private void deleteUser() {
        System.out.print("Enter user ID to delete: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        String response = adminController.deleteUser(userId);
        System.out.println(response);
    }

    private void updateUser() {
        System.out.print("Enter user ID to update: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        if (!Validator.isValidName(name)) {
            System.out.println("Error: Name must contain only letters (A-Z, а-я).");
            return;
        }

        System.out.print("Enter new phone number: ");
        String phoneNumber = scanner.nextLine();
        if (!Validator.isValidPhoneNumber(phoneNumber)) {
            System.out.println("Error: Phone number must be 11 digits and start with 8 or +7.");
            return;
        }

        System.out.print("Enter new email: ");
        String email = scanner.nextLine();

        System.out.print("Enter new password: ");
        String password = scanner.nextLine();

        System.out.print("Enter new role (user/admin/manager): ");
        String role = scanner.nextLine();

        Double cash = null;
        if (role.equalsIgnoreCase("user")) {
            System.out.print("Enter new cash balance: ");
            cash = scanner.nextDouble();
            scanner.nextLine();
        }

        String response = adminController.updateUser(userId, name, email, phoneNumber, password, role, cash);
        System.out.println(response);
    }

    private void createAdminOrManager() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        if (!Validator.isValidName(name)) {
            System.out.println("Error: Name must contain only letters (A-Z, а-я).");
            return;
        }

        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        if (!Validator.isEmailValid(email)) {
            System.out.println("Error: Invalid email format!");
            return;
        }

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        if (!Validator.isValidPhoneNumber(phoneNumber)) {
            System.out.println("Error: Phone number must be 11 digits and start with 8 or +7.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        if (!Validator.isPasswordValid(password)) {
            System.out.println("Error: Password must be at least 6 characters long!");
            return;
        }

        System.out.print("Enter role (admin/manager): ");
        String role = scanner.nextLine();
        if (!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("manager")) {
            System.out.println("Error: Role must be 'admin' or 'manager'.");
            return;
        }

        String response = adminController.createAdminOrManager(name, email, phoneNumber, password, role);
        System.out.println(response);
    }

    private void addVehicle() {
        System.out.print("Enter vehicle brand: ");
        String brand = scanner.nextLine();
        if (!Validator.isValidBrandOrModel(brand)) {
            System.out.println("Error: Brand must contain only letters, numbers, spaces, or '-'.");
            return;
        }

        System.out.print("Enter vehicle model: ");
        String model = scanner.nextLine();
        if (!Validator.isValidBrandOrModel(model)) {
            System.out.println("Error: Model must contain only letters, numbers, spaces, or '-'.");
            return;
        }

        System.out.print("Enter vehicle type (SUV, Sedan, etc.): ");
        String type = scanner.nextLine();
        if (!Validator.isValidName(type)) {
            System.out.println("Error: Vehicle type must contain only letters.");
            return;
        }

        System.out.print("Enter vehicle price: ");
        if (!scanner.hasNextDouble()) {
            System.out.println("Error: Please enter a valid price (number).");
            scanner.nextLine();
            return;
        }
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter vehicle release year: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Error: Please enter a valid year.");
            scanner.nextLine();
            return;
        }
        int releaseYear = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter 360 Camera (Yes/No): ");
        String camera360 = scanner.nextLine();
        if (!camera360.equalsIgnoreCase("Yes") && !camera360.equalsIgnoreCase("No")) {
            System.out.println("Error: Please enter 'Yes' or 'No'.");
            return;
        }

        System.out.print("Enter Cruise Control (Standard/Adaptive/None): ");
        String cruiseControl = scanner.nextLine();
        if (!cruiseControl.equalsIgnoreCase("Standard") && !cruiseControl.equalsIgnoreCase("Adaptive") && !cruiseControl.equalsIgnoreCase("None")) {
            System.out.println("Error: Please enter 'Standard', 'Adaptive', or 'None'.");
            return;
        }

        System.out.print("Enter Autopilot (Enabled/Disabled): ");
        String autopilot = scanner.nextLine();
        if (!autopilot.equalsIgnoreCase("Enabled") && !autopilot.equalsIgnoreCase("Disabled")) {
            System.out.println("Error: Please enter 'Enabled' or 'Disabled'.");
            return;
        }

        String response = adminController.addVehicle(brand, model, type, price, releaseYear, camera360, cruiseControl, autopilot);
        System.out.println(response);
    }


    private void deleteVehicle() {
        System.out.print("Enter vehicle ID to delete: ");
        int vehicleId = scanner.nextInt();
        scanner.nextLine();

        String response = adminController.deleteVehicle(vehicleId);
        System.out.println(response);
    }

    private void viewAllVehicles() {
        String vehicles = vehicleController.getAllVehicles();

        if (vehicles == null || vehicles.isEmpty()) {
            System.out.println("No vehicles available.");
        } else {
            System.out.println(vehicles);
        }
    }


    private void viewVehicleById() {
        System.out.print("Enter vehicle ID: ");
        int vehicleId = scanner.nextInt();
        scanner.nextLine();

        String vehicleDetails = vehicleController.getVehicleById(vehicleId);

        if (vehicleDetails != null && !vehicleDetails.isEmpty()) {
            System.out.println(vehicleDetails);
        }
    }
}
