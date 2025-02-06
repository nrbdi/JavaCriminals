import models.User;
import java.util.Scanner;
import controllers.UserController;
import controllers.VehicleController;

public class ApplicationMenu {
    private final UserController userController;
    private final VehicleController vehicleController;
    private final MyApplication userApp;
    private final MyApplication_2 adminApp;
    private final Scanner scanner;

    public ApplicationMenu(UserController userController, VehicleController vehicleController, MyApplication userApp, MyApplication_2 adminApp) {
        this.userController = userController;
        this.vehicleController = vehicleController;
        this.userApp = userApp;
        this.adminApp = adminApp;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n1. Add new user");
            System.out.println("2. Update existing user");
            System.out.println("3. Login");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка ввода

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
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter phone number:");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        System.out.println("Enter role (user/admin/manager):");
        String role = scanner.nextLine();
        System.out.println("Enter cash amount:");
        double cash = scanner.nextDouble();
        scanner.nextLine();

        String response = userController.createUser(name, email, phoneNumber, password, role, cash);
        System.out.println(response);
    }

    private void updateUser() {
        System.out.println("Enter user ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter new name:");
        String name = scanner.nextLine();
        System.out.println("Enter new email:");
        String email = scanner.nextLine();
        System.out.println("Enter new phone number:");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter new password:");
        String password = scanner.nextLine();
        System.out.println("Enter new role (user/admin/manager):");
        String role = scanner.nextLine();
        System.out.println("Enter new cash amount:");
        double cash = scanner.nextDouble();
        scanner.nextLine();

        String response = userController.updateUser(id, name, email, phoneNumber, password, role, cash);
        System.out.println(response);
    }

    private void loginAndRedirect() {
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        User user = userController.loginUser(email, password);
        if (user == null) {
            System.out.println("Login failed. Please check your credentials.");
            return;
        }

        System.out.printf("Login successful! User ID: %d, Role: %s, Balance: %.2f%n",
                user.getId(), user.getRole(), user.getCash());

        switch (user.getRole().toLowerCase()) {
            case "admin" -> adminApp.start("admin");
            case "manager" -> adminApp.start("manager");
            case "user" -> userApp.start();
            default -> System.out.println("Unknown role. Access denied.");
        }
    }
}
