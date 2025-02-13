import controllers.AdministrationController;
import controllers.UserController;
import controllers.VehicleController;
import models.User;
import utils.Validator;

import java.util.List;
import java.util.Scanner;

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

            String input = scanner.nextLine();  // Читаем ввод как строку
            if (!input.matches("\\d+")) { // Проверяем, что ввод - это число
                System.out.println("Error: Please enter a valid number.");
                continue;
            }

            int choice = Integer.parseInt(input); // Преобразуем строку в число

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
        }
    }



    private void managerMenu() {
        while (true) {
            System.out.println("\nManager Menu:");
            System.out.println("1. Add a vehicle");
            System.out.println("2. Delete a vehicle");
            System.out.println("3. View all vehicles");
            System.out.println("4. View vehicle by ID");
            System.out.println("5. Show purchase & reservation report");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();  // Читаем ввод как строку
            if (!input.matches("\\d+")) { // Проверяем, что ввод - это число
                System.out.println("Error: Please enter a valid number.");
                continue;
            }

            int choice = Integer.parseInt(input); // Преобразуем строку в число

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
        while (!scanner.hasNextInt()) {
            System.out.println("Error: User ID must be a positive number.");
            scanner.next();
        }
        int userId = scanner.nextInt();
        scanner.nextLine();

        String name;
        do {
            System.out.print("Enter new name: ");
            name = scanner.nextLine();
            if (!Validator.isValidName(name)) {
                System.out.println("Error: Name must contain only letters (Latin or Cyrillic)!");
            }
        } while (!Validator.isValidName(name));

        String email;
        do {
            System.out.print("Enter new email: ");
            email = scanner.nextLine();
            if (!Validator.isEmailValid(email)) {
                System.out.println("Error: Invalid email format! Please try again.");
            }
        } while (!Validator.isEmailValid(email));

        String phoneNumber;
        do {
            System.out.print("Enter new phone number: ");
            phoneNumber = scanner.nextLine();
            if (!Validator.isValidPhoneNumber(phoneNumber)) {
                System.out.println("Error: Phone number must start with 8 or +7 and contain exactly 11 digits.");
            }
        } while (!Validator.isValidPhoneNumber(phoneNumber));

        String password;
        do {
            System.out.print("Enter new password: ");
            password = scanner.nextLine();
            if (!Validator.isPasswordValid(password)) {
                System.out.println("Error: Password must be at least 6 characters long!");
            }
        } while (!Validator.isPasswordValid(password));

        System.out.print("Enter new role (user/admin/manager): ");
        String role = scanner.nextLine().toLowerCase();

        Double cash = null;
        if (role.equalsIgnoreCase("user")) {
            do {
                System.out.print("Enter new cash balance: ");
                while (!scanner.hasNextDouble()) {
                    System.out.println("Error: Please enter a valid cash amount.");
                    scanner.next();
                }
                cash = scanner.nextDouble();
                scanner.nextLine();
                if (!Validator.isPositiveNumber(cash)) {
                    System.out.println("Error: Cash cannot be negative!");
                }
            } while (!Validator.isPositiveNumber(cash));
        }

        String response = adminController.updateUser(userId, name, email, phoneNumber, password, role, cash);
        System.out.println(response);
    }

    private void createAdminOrManager() {
        String name;
        do {
            System.out.print("Enter name: ");
            name = scanner.nextLine();
            if (!Validator.isValidName(name)) {
                System.out.println("Error: Name must contain only letters (Latin or Cyrillic)!");
            }
        } while (!Validator.isValidName(name));

        String email;
        do {
            System.out.print("Enter email: ");
            email = scanner.nextLine();
            if (!Validator.isEmailValid(email)) {
                System.out.println("Error: Invalid email format! Please try again.");
            }
        } while (!Validator.isEmailValid(email));

        String phoneNumber;
        do {
            System.out.print("Enter phone number: ");
            phoneNumber = scanner.nextLine();
            if (!Validator.isValidPhoneNumber(phoneNumber)) {
                System.out.println("Error: Phone number must start with 8 or +7 and contain exactly 11 digits.");
            }
        } while (!Validator.isValidPhoneNumber(phoneNumber));

        String password;
        do {
            System.out.print("Enter password: ");
            password = scanner.nextLine();
            if (!Validator.isPasswordValid(password)) {
                System.out.println("Error: Password must be at least 6 characters long!");
            }
        } while (!Validator.isPasswordValid(password));

        String role;
        do {
            System.out.print("Enter role (admin/manager): ");
            role = scanner.nextLine();
            if (!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("manager")) {
                System.out.println("Error: Only admin can create new admins or managers!");
            }
        } while (!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("manager"));

        String response = adminController.createAdminOrManager(name, email, phoneNumber, password, role);
        System.out.println(response);
    }

    private void addVehicle() {
        System.out.print("Enter vehicle brand: ");
        String brand;
        while (true) {
            brand = scanner.nextLine();
            if (!brand.matches("[a-zA-Zа-яА-Я]+")) {
                System.out.println("Error: Brand can only contain letters (Latin/Cyrillic)!");
            } else {
                break;
            }
        }

        System.out.print("Enter vehicle model: ");
        String model;
        while (true) {
            model = scanner.nextLine();
            if (!Validator.isValidBrandOrModel(model)) {
                System.out.println("Error: Model can only contain letters and numbers!");
            } else {
                break;
            }
        }

        System.out.print("Enter vehicle type: ");
        String type;
        while (true) {
            type = scanner.nextLine();
            if (!Validator.isValidTypeOrColor(type)) {
                System.out.println("Error: Vehicle type can only contain letters!");
            } else {
                break;
            }
        }

        double price;
        while (true) {
            System.out.print("Enter vehicle price: ");
            if (scanner.hasNextDouble()) {
                price = scanner.nextDouble();
                if (Validator.isPositiveNumber(price)) {
                    scanner.nextLine();
                    break;
                }
            }
            System.out.println("Error: Price must be a positive number!");
            scanner.nextLine();
        }

        int year;
        while (true) {
            System.out.print("Enter vehicle release year: ");
            if (scanner.hasNextInt()) {
                year = scanner.nextInt();
                if (Validator.isPositiveInteger(year)) {
                    scanner.nextLine();
                    break;
                }
            }
            System.out.println("Error: Please enter a valid year (only numbers)!");
            scanner.nextLine();
        }

        double enginePower;
        while (true) {
            System.out.print("Enter engine power (e.g., 2.4 or 3.5): ");
            if (scanner.hasNextDouble()) {
                enginePower = scanner.nextDouble();
                if (Validator.isPositiveNumber(enginePower)) {
                    scanner.nextLine();
                    break;
                }
            }
            System.out.println("Error: Engine power must be a positive number!");
            scanner.nextLine();
        }

        System.out.print("Enter fuel type (Petrol/Diesel/Electric/Hybrid): ");
        String fuelType;
        while (true) {
            fuelType = scanner.nextLine();
            if (fuelType.equalsIgnoreCase("Petrol") ||
                    fuelType.equalsIgnoreCase("Diesel") ||
                    fuelType.equalsIgnoreCase("Electric") ||
                    fuelType.equalsIgnoreCase("Hybrid")) {
                break;
            }
            System.out.println("Error: Fuel type can only be 'Petrol', 'Diesel', 'Electric', or 'Hybrid'!");
        }

        System.out.print("Enter transmission type (Manual/Automatic): ");
        String transmission;
        while (true) {
            transmission = scanner.nextLine();
            if (transmission.equalsIgnoreCase("Manual") || transmission.equalsIgnoreCase("Automatic")) {
                break;
            }
            System.out.println("Error: Transmission type can only be 'Manual' or 'Automatic'!");
        }

        System.out.print("Enter color: ");
        String color;
        while (true) {
            color = scanner.nextLine();
            if (Validator.isValidTypeOrColor(color)) {
                break;
            }
            System.out.println("Error: Color can only contain letters!");
        }

        double mileage;
        while (true) {
            System.out.print("Enter mileage (km): ");
            if (scanner.hasNextDouble()) {
                mileage = scanner.nextDouble();
                if (Validator.isPositiveNumber(mileage)) {
                    scanner.nextLine();
                    break;
                }
            }
            System.out.println("Error: Mileage cannot be negative!");
            scanner.nextLine();
        }

        System.out.print("Enter 360 Camera (Yes/No): ");
        String camera360;
        while (true) {
            camera360 = scanner.nextLine();
            if (Validator.isValidCameraOption(camera360)) {
                break;
            }
            System.out.println("Error: Enter 'Yes' or 'No' for 360 Camera.");
        }

        System.out.print("Enter Cruise Control (Standard/Adaptive/None): ");
        String cruiseControl;
        while (true) {
            cruiseControl = scanner.nextLine();
            if (Validator.isValidCruiseControlOption(cruiseControl)) {
                break;
            }
            System.out.println("Error: Enter 'Standard', 'Adaptive', or 'None' for Cruise Control.");
        }

        System.out.print("Enter Autopilot (Enabled/Disabled): ");
        String autopilot;
        while (true) {
            autopilot = scanner.nextLine();
            if (Validator.isValidAutopilotOption(autopilot)) {
                break;
            }
            System.out.println("Error: Enter 'Enabled' or 'Disabled' for Autopilot.");
        }

        String response = adminController.addVehicle(brand, model, type, price, year,
                camera360, cruiseControl, autopilot,
                enginePower, fuelType, transmission, color, mileage);
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
