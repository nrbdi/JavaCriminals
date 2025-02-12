import controllers.AdministrationController;
import controllers.VehicleController;
import controllers.UserController;
import java.util.Scanner;
import models.User;

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
            System.out.println("2. View user by ID");
            System.out.println("3. Delete a user");
            System.out.println("4. Show purchase & reservation report");
            System.out.println("5. Update existing user");
            System.out.println("6. Create admin/manager");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewAllUsers();
                case 2 -> viewUserById();
                case 3 -> deleteUser();
                case 4 -> showPurchaseReport();
                case 5 -> updateUser();
                case 6 -> createAdminOrManager();
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

    private void viewUserById() {
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        User user = userController.getUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
        } else {
            System.out.printf("User Details:\n- Name: %s\n- Email: %s\n- Phone: %s\n- Role: %s\n- Balance: %.2f%n",
                    user.getName(), user.getEmail(), user.getPhoneNumber(), user.getRole(), user.getCash());
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

        System.out.print("Enter new email: ");
        String email = scanner.nextLine();

        System.out.print("Enter new phone number: ");
        String phoneNumber = scanner.nextLine();

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

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter role (admin/manager): ");
        String role = scanner.nextLine();

        String response = adminController.createAdminOrManager(name, email, phoneNumber, password, role);
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

    private void viewAllVehicles() {
        String vehicles = vehicleController.getAllVehicles();
        System.out.println(vehicles);
    }

    private void viewVehicleById() {
        System.out.print("Enter vehicle ID: ");
        int vehicleId = scanner.nextInt();
        scanner.nextLine();

        String vehicle = vehicleController.getVehicleById(vehicleId);
        System.out.println(vehicle);
    }
}
