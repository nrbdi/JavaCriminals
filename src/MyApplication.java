import controllers.VehicleController;
import models.User;

import java.util.Scanner;

public class MyApplication {
    private final VehicleController vehicleController;
    private final Scanner scanner;
    private final User currentUser;

    public MyApplication(VehicleController vehicleController, User currentUser) {
        this.vehicleController = vehicleController;
        this.currentUser = currentUser;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\nWelcome to the Vehicle Management Application!");
            System.out.println("1. View all vehicles");
            System.out.println("2. Filter vehicles by type");
            System.out.println("3. Filter vehicles by brand");
            System.out.println("4. Purchase a vehicle");
            System.out.println("5. Reserve a vehicle");
            System.out.println("6. View vehicle details by ID");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();  // Читаем ввод как строку
            if (!input.matches("\\d+")) { // Проверяем, что ввод - это число
                System.out.println("Error: Please enter a valid number.");
                continue;
            }

            int choice = Integer.parseInt(input); // Преобразуем строку в число

            switch (choice) {
                case 1 -> viewAllVehicles();
                case 2 -> filterVehiclesByType();
                case 3 -> filterVehiclesByBrand();
                case 4 -> purchaseVehicle();
                case 5 -> reserveVehicle();
                case 6 -> viewVehicleDetailsById();
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private void viewAllVehicles() {
        String vehicles = vehicleController.getAllVehicles();
        System.out.println(vehicles);
    }

    private void filterVehiclesByType() {
        System.out.print("Enter vehicle type (e.g., SUV, Sedan): ");
        String type = scanner.nextLine();
        String vehicles = vehicleController.getVehiclesByType(type);
        System.out.println(vehicles);
    }

    private void filterVehiclesByBrand() {
        System.out.print("Enter vehicle brand (e.g., Toyota, BMW): ");
        String brand = scanner.nextLine();
        String vehicles = vehicleController.getVehiclesByBrand(brand);
        System.out.println(vehicles);
    }

    private void purchaseVehicle() {
        System.out.print("Enter vehicle ID to purchase: ");
        int vehicleId = scanner.nextInt();
        scanner.nextLine();

        String response = vehicleController.purchaseVehicle(vehicleId, currentUser.getId());
        System.out.println(response);
    }

    private void reserveVehicle() {
        System.out.print("Enter vehicle ID to reserve: ");
        int vehicleId = scanner.nextInt();
        scanner.nextLine();

        String response = vehicleController.reserveVehicle(vehicleId, currentUser.getId());
        System.out.println(response);
    }

    private void viewVehicleDetailsById() {
        System.out.print("Enter vehicle ID: ");
        String input = scanner.nextLine();

        if (!input.matches("\\d+")) {
            System.out.println("Error: Please enter a valid vehicle ID.");
            return;
        }

        int vehicleId = Integer.parseInt(input);
        String details = vehicleController.getVehicleDetailsById(vehicleId);

        if (details != null && !details.isEmpty()) {
            System.out.println(details);
        }
    }
}
