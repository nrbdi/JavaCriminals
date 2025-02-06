import controllers.AdministrationController;
import controllers.VehicleController;
import controllers.UserController;
import java.util.Scanner;

public class MyApplication_2 {
    private final AdministrationController adminController;
    private final VehicleController vehicleController;
    private final Scanner scanner;

    public MyApplication_2(AdministrationController adminController, VehicleController vehicleController) {
        this.adminController = adminController;
        this.vehicleController = vehicleController;
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
            System.out.println("2. Delete a user");
            System.out.println("3. Show purchase & reservation report");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewAllUsers();
                case 2 -> deleteUser();
                case 3 -> showPurchaseReport();
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
            System.out.println("3. Show purchase & reservation report");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addVehicle();
                case 2 -> deleteVehicle();
                case 3 -> showPurchaseReport();
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please choose again.");
            }
        }
    }

    private void showPurchaseReport() {
        System.out.println("\nFetching purchase and reservation report...");
        vehicleController.showJoinedTableView();
    }

    private void viewAllUsers() {
        String users = adminController.getAllUsers();
        System.out.println(users);
    }

    private void deleteUser() {
        System.out.print("Enter user ID to delete: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        String response = adminController.deleteUser(userId);
        System.out.println(response);
    }

    private void addVehicle() {
        System.out.print("Enter vehicle brand: ");
        String brand = scanner.nextLine();
        System.out.print("Enter vehicle model: ");
        String model = scanner.nextLine();
        System.out.print("Enter vehicle type: ");
        String type = scanner.nextLine();
        System.out.print("Enter vehicle price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter vehicle release year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        String response = adminController.addVehicle(brand, model, type, price, year);
        System.out.println(response);
    }

    private void deleteVehicle() {
        System.out.print("Enter vehicle ID to delete: ");
        int vehicleId = scanner.nextInt();
        scanner.nextLine();

        String response = adminController.deleteVehicle(vehicleId);
        System.out.println(response);
    }
}
