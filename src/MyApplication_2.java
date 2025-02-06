import controllers.interfaces.IAdministrationController;
import controllers.interfaces.IUserController;
import models.Vehicle;
import models.Characteristics;

import java.util.Scanner;

public class MyApplication_2 {
    private final IAdministrationController adminController;
    private final IUserController userController;
    private final Scanner scanner = new Scanner(System.in);

    public MyApplication_2(IAdministrationController adminController, IUserController userController) {
        this.adminController = adminController;
        this.userController = userController;
    }

    public void start(String role) {
        if ("admin".equalsIgnoreCase(role)) {
            adminMenu();
        } else if ("manager".equalsIgnoreCase(role)) {
            managerMenu();
        } else {
            System.out.println("Invalid role. Exiting application.");
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Delete a user");
            System.out.println("2. View all users");
            System.out.println("3. Add a vehicle");
            System.out.println("4. Delete a vehicle");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> deleteUser();
                case 2 -> viewAllUsers();
                case 3 -> addVehicle();
                case 4 -> deleteVehicle();
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void managerMenu() {
        while (true) {
            System.out.println("\nManager Menu:");
            System.out.println("1. Add a vehicle");
            System.out.println("2. Delete a vehicle");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addVehicle();
                case 2 -> deleteVehicle();
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void deleteUser() {
        System.out.print("Enter user ID to delete: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        boolean success = adminController.deleteUser(userId);
        if (success) {
            System.out.println("User with ID " + userId + " has been successfully deleted.");
        } else {
            System.out.println("Failed to delete user. Ensure the ID is correct.");
        }
    }

    private void viewAllUsers() {
        String allUsers = userController.getAllUsers();
        System.out.println("Users:\n" + allUsers);
    }

    private void addVehicle() {
        System.out.println("Enter vehicle details:");
        System.out.print("Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Type (SUV, Sedan, etc.): ");
        String type = scanner.nextLine();
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        System.out.print("Release Year: ");
        int releaseYear = scanner.nextInt();
        scanner.nextLine(); // Clear the newline
        System.out.print("Status (available, sold, reserved): ");
        String status = scanner.nextLine();

        Vehicle vehicle = new Vehicle(brand, model, type, price, releaseYear, status);

        System.out.println("Enter vehicle characteristics:");
        System.out.print("Engine Power (L): ");
        double enginePower = scanner.nextDouble();
        scanner.nextLine(); // Clear the newline
        System.out.print("Fuel Type: ");
        String fuelType = scanner.nextLine();
        System.out.print("Transmission: ");
        String transmission = scanner.nextLine();
        System.out.print("Color: ");
        String color = scanner.nextLine();
        System.out.print("Mileage (km): ");
        double mileage = scanner.nextDouble();
        scanner.nextLine(); // Clear the newline

        Characteristics characteristics = new Characteristics(0, 0, enginePower, fuelType, transmission, color, mileage);

        boolean success = adminController.addVehicle(vehicle, characteristics);
        if (success) {
            System.out.println("Vehicle has been successfully added.");
        } else {
            System.out.println("Failed to add vehicle.");
        }
    }


    private void deleteVehicle() {
        System.out.print("Enter vehicle ID to delete: ");
        int vehicleId = scanner.nextInt();
        scanner.nextLine();
        boolean success = adminController.deleteVehicle(vehicleId);
        if (success) {
            System.out.println("Vehicle with ID " + vehicleId + " has been successfully deleted.");
        } else {
            System.out.println("Failed to delete vehicle. Ensure the ID is correct.");
        }
    }
}
