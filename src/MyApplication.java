import java.util.Scanner;
import controllers.interfaces.IUserController;
import controllers.interfaces.IVehicleController;
import controllers.interfaces.ICharacteristicsController;

public class MyApplication {
    private final IUserController userController;
    private final IVehicleController vehicleController;
    private final ICharacteristicsController characteristicsController;
    private final Scanner scanner = new Scanner(System.in);
    private boolean isLoggedIn = false;
    private int loggedInUserId;
    private double loggedInUserCash;

    public MyApplication(IUserController userController, IVehicleController vehicleController, ICharacteristicsController characteristicsController) {
        this.userController = userController;
        this.vehicleController = vehicleController;
        this.characteristicsController = characteristicsController;
    }

    public void start() {
        while (true) {
            if (!isLoggedIn) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\nWelcome to the User Management Application!");
        System.out.println("1. Add new user");
        System.out.println("2. Update existing user");
        System.out.println("3. Get user by ID");
        System.out.println("4. Show all users");
        System.out.println("5. Login");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> addUser();
            case 2 -> updateUser();
            case 3 -> getUserById();
            case 4 -> showAllUsers();
            case 5 -> loginUserMenu();
            case 0 -> {
                System.out.println("Application closed.");
                System.exit(0);
            }
            default -> System.out.println("Choose an option between 0 and 5.");
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
        System.out.println("Enter role (user/admin):");
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
        System.out.println("Enter new role (user/admin):");
        String role = scanner.nextLine();
        System.out.println("Enter new cash amount:");
        double cash = scanner.nextDouble();
        scanner.nextLine();

        String response = userController.updateUser(id, name, email, phoneNumber, password, role, cash);
        System.out.println(response);
    }

    private void getUserById() {
        System.out.println("Enter user ID:");
        int id = scanner.nextInt();
        scanner.nextLine();

        String response = userController.getUserById(id);
        System.out.println(response);
    }

    private void showAllUsers() {
        String response = userController.getAllUsers();
        System.out.println(response);
    }

    private void loginUserMenu() {
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        String response = userController.loginUser(email, password);
        System.out.println(response);

        if (response.startsWith("Login successful!")) {
            isLoggedIn = true;
            try {
                String[] parts = response.replace("Login successful! ", "").split(",");
                loggedInUserId = Integer.parseInt(parts[0].trim());
                loggedInUserCash = Double.parseDouble(parts[1].trim());
                System.out.println("Login successful! User ID: " + loggedInUserId + ", Balance: " + loggedInUserCash);
                showMainMenu();
            } catch (Exception e) {
                System.out.println("Failed to parse user data: " + e.getMessage());
                isLoggedIn = false;
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\nWelcome to the Vehicle Management Application!");
        System.out.println("1. View vehicle catalog");
        System.out.println("2. Logout");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> showVehicleCatalog();
            case 2 -> {
                isLoggedIn = false;
                System.out.println("You have logged out.");
            }
            default -> System.out.println("Choose an option between 1 and 2.");
        }
    }

    private void showVehicleCatalog() {
        while (true) {
            System.out.println("\nVehicle Catalog:");
            System.out.println("1. Show all vehicles");
            System.out.println("2. Filter by vehicle type");
            System.out.println("3. Filter by brand");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    String allVehicles = vehicleController.getAllVehicles();
                    System.out.println(allVehicles);
                    showVehicleDetailsMenu();
                }
                case 2 -> {
                    System.out.print("Enter vehicle type (Crossover, Sedan, SUV): ");
                    String type = scanner.nextLine();
                    String vehiclesByType = vehicleController.getVehiclesByType(type);
                    System.out.println(vehiclesByType);
                    showVehicleDetailsMenu();
                }
                case 3 -> {
                    System.out.print("Enter brand (Kia, Hyundai, Mercedes, Toyota, BMW): ");
                    String brand = scanner.nextLine();
                    String vehiclesByBrand = vehicleController.getVehiclesByBrand(brand);
                    System.out.println(vehiclesByBrand);
                    showVehicleDetailsMenu();
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Choose an option between 0 and 3.");
            }
        }
    }

    private void showVehicleDetailsMenu() {
        while (true) {
            System.out.println("\n1. View detailed vehicle characteristics");
            System.out.println("2. Back");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter vehicle ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    String details = characteristicsController.getCharacteristicsByVehicleId(id);
                    if (details == null || details.isEmpty()) {
                        System.out.println("No characteristics found for vehicle ID " + id + ".");
                    } else {
                        System.out.println("Vehicle characteristics:");
                        System.out.println(details);
                        String vehicleStatus = vehicleController.getVehicleStatus(id);
                        if ("available".equalsIgnoreCase(vehicleStatus)) {
                            showPurchaseOrReserveMenu(id);
                        } else {
                            System.out.println("This vehicle is not available for purchase or reservation.");
                        }
                    }
                }
                case 2 -> {
                    return;
                }
                default -> System.out.println("Choose an option between 1 and 2.");
            }
        }
    }

    private void showPurchaseOrReserveMenu(int vehicleId) {
        System.out.println("\nThis vehicle is available. What would you like to do?");
        System.out.println("1. Purchase this vehicle");
        System.out.println("2. Reserve this vehicle");
        System.out.println("3. Back");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1 -> {
                System.out.print("Confirm purchase? (yes/no): ");
                String confirmation = scanner.nextLine();
                if ("yes".equalsIgnoreCase(confirmation)) {
                    double price = vehicleController.getVehiclePrice(vehicleId);
                    String purchaseResult = vehicleController.purchaseVehicle(vehicleId, loggedInUserId, loggedInUserCash, 0);
                    System.out.println(purchaseResult);

                    if (purchaseResult.startsWith("Purchase successful!")) {
                        loggedInUserCash -= price;
                        System.out.println("Your new balance: " + loggedInUserCash);
                    }
                }
            }
            case 2 -> {
                System.out.print("Confirm reservation? (yes/no): ");
                String confirmation = scanner.nextLine();
                if ("yes".equalsIgnoreCase(confirmation)) {
                    String reserveResult = vehicleController.reserveVehicle(vehicleId, loggedInUserId);
                    System.out.println(reserveResult);
                }
            }
            case 3 -> {
                return;
            }
            default -> System.out.println("Choose an option between 1 and 3.");
        }
    }
}