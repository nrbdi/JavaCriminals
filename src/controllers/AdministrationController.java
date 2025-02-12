package controllers;

import controllers.interfaces.IAdministrationController;
import controllers.interfaces.IUserController;
import models.Characteristics;
import models.User;
import models.Vehicle;
import repositories.interfaces.IAdministrationRepository;
import utils.Validator;

import java.util.Scanner;

public class AdministrationController implements IAdministrationController {
    private final IAdministrationRepository adminRepository;
    private final IUserController userController;
    private final Scanner scanner;

    public AdministrationController(IAdministrationRepository adminRepository, IUserController userController) {
        this.adminRepository = adminRepository;
        this.userController = userController;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String deleteUser(int userId) {
        boolean success = adminRepository.deleteUserById(userId);
        return success ? "User successfully deleted!" : "Failed to delete user.";
    }

    @Override
    public String addVehicle(String brand, String model, String type, double price, int year) {
        System.out.print("Enter engine power (e.g., 2.4 or 3.5): ");
        double enginePower = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter fuel type: ");
        String fuelType = scanner.nextLine();

        System.out.print("Enter transmission type: ");
        String transmission = scanner.nextLine();

        System.out.print("Enter color: ");
        String color = scanner.nextLine();

        System.out.print("Enter mileage (km): ");
        double mileage = scanner.nextDouble();
        scanner.nextLine();

        Vehicle vehicle = new Vehicle(brand, model, type, price, year, "available");
        Characteristics characteristics = new Characteristics(
                vehicle.getId(), enginePower, fuelType, transmission, color, mileage
        );

        boolean success = adminRepository.addVehicle(vehicle, characteristics);
        return success ? "Vehicle successfully added!" : "Failed to add vehicle.";
    }

    @Override
    public String updateUser(int id, String name, String email, String phoneNumber, String password, String role, Double cash) {
        if (!Validator.isPositiveInteger(id)) {
            return "Error: User ID must be a positive number.";
        }
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

        double finalCash = (role.equalsIgnoreCase("user") && cash != null) ? cash : 0.0;
        User user = new User(id, name, email, phoneNumber, password, role, finalCash);
        boolean updated = adminRepository.updateUser(user);
        return updated ? "User successfully updated!" : "Failed to update user.";
    }

    @Override
    public String deleteVehicle(int vehicleId) {
        boolean success = adminRepository.deleteVehicleById(vehicleId);
        return success ? "Vehicle successfully deleted!" : "Failed to delete vehicle.";
    }

    public String getAllUsers() {
        return userController.getAllUsers();
    }

    public String createAdminOrManager(String name, String email, String phoneNumber, String password, String role) {
        if (!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("manager")) {
            return "Error: Only admin can create new admins or managers!";
        }

        User user = new User(0, name, email, phoneNumber, password, role, 0.0);
        boolean created = adminRepository.createAdminOrManager(user);

        if (created) {
            return "Admin/Manager successfully created: " + user.getName() + " (" + user.getEmail() + ")";
        } else {
            System.out.println("Error: Failed to create admin/manager. Check database constraints or duplicates.");
            return "Failed to create admin/manager.";
        }
    }

}
