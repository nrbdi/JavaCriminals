package controllers;

import controllers.interfaces.IAdministrationController;
import controllers.interfaces.IUserController;
import models.Characteristics;
import models.Vehicle;
import repositories.interfaces.IAdministrationRepository;

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
    public String deleteVehicle(int vehicleId) {
        boolean success = adminRepository.deleteVehicleById(vehicleId);
        return success ? "Vehicle successfully deleted!" : "Failed to delete vehicle.";
    }

    public String getAllUsers() {
        return userController.getAllUsers();
    }
}
