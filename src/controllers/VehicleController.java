package controllers;

import controllers.interfaces.IVehicleController;
import models.Vehicle;
import repositories.interfaces.IVehicleRepository;
import controllers.interfaces.IUserController;

import java.util.List;

public class VehicleController implements IVehicleController {
    private final IVehicleRepository vehicleRepository;
    private final IUserController userController;

    public VehicleController(IVehicleRepository vehicleRepository, IUserController userController) {
        this.vehicleRepository = vehicleRepository;
        this.userController = userController;
    }

    @Override
    public String getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.getAllVehicles();
        if (vehicles.isEmpty()) {
            return "No vehicles available.";
        }

        StringBuilder sb = new StringBuilder("List of vehicles:\n");
        for (Vehicle vehicle : vehicles) {
            sb.append(String.format(
                    "ID: %d\nBrand: %s\nModel: %s\nType: %s\nPrice: %.2f\nRelease Year: %d\nStatus: %s\n\n",
                    vehicle.getId(),
                    vehicle.getBrand(),
                    vehicle.getModel(),
                    vehicle.getVehicleType(),
                    vehicle.getPrice(),
                    vehicle.getReleaseYear(),
                    vehicle.getStatus()
            ));
        }
        return sb.toString();
    }

    @Override
    public String getVehiclesByType(String type) {
        List<Vehicle> vehicles = vehicleRepository.getVehiclesByType(type);
        if (vehicles.isEmpty()) {
            return "No vehicles of type \"" + type + "\" found.";
        }

        StringBuilder sb = new StringBuilder("Vehicles of type \"" + type + "\":\n");
        for (Vehicle vehicle : vehicles) {
            sb.append(String.format(
                    "ID: %d\nBrand: %s\nModel: %s\nType: %s\nPrice: %.2f\nRelease Year: %d\nStatus: %s\n\n",
                    vehicle.getId(),
                    vehicle.getBrand(),
                    vehicle.getModel(),
                    vehicle.getVehicleType(),
                    vehicle.getPrice(),
                    vehicle.getReleaseYear(),
                    vehicle.getStatus()
            ));
        }
        return sb.toString();
    }

    @Override
    public String getVehiclesByBrand(String brand) {
        List<Vehicle> vehicles = vehicleRepository.getVehiclesByBrand(brand);
        if (vehicles.isEmpty()) {
            return "No vehicles of brand \"" + brand + "\" found.";
        }

        StringBuilder sb = new StringBuilder("Vehicles of brand \"" + brand + "\":\n");
        for (Vehicle vehicle : vehicles) {
            sb.append(String.format(
                    "ID: %d\nBrand: %s\nModel: %s\nType: %s\nPrice: %.2f\nRelease Year: %d\nStatus: %s\n\n",
                    vehicle.getId(),
                    vehicle.getBrand(),
                    vehicle.getModel(),
                    vehicle.getVehicleType(),
                    vehicle.getPrice(),
                    vehicle.getReleaseYear(),
                    vehicle.getStatus()
            ));
        }
        return sb.toString();
    }

    @Override
    public String getVehicleStatus(int vehicleId) {
        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
        if (vehicle == null) {
            return "Vehicle not found.";
        }
        return vehicle.getStatus();
    }

    @Override
    public String purchaseVehicle(int vehicleId, int userId, double userCash, double discount) {
        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
        if (vehicle == null) {
            return "Vehicle not found.";
        }

        if (!"available".equalsIgnoreCase(vehicle.getStatus())) {
            return "This vehicle is not available for purchase.";
        }

        double finalPrice = vehicle.getPrice() - discount;
        if (userCash < finalPrice) {
            return "Insufficient funds to purchase this vehicle.";
        }

        boolean updatedVehicleStatus = vehicleRepository.updateVehicleStatus(vehicleId, "sold");
        if (!updatedVehicleStatus) {
            return "Failed to update vehicle status.";
        }

        double newBalance = userCash - finalPrice;
        boolean updatedUserBalance = userController.updateUserBalance(userId, newBalance);
        if (!updatedUserBalance) {
            return "Failed to update user balance.";
        }

        return "Purchase successful! Vehicle ID: " + vehicleId + " has been sold. Your new balance: " + newBalance;
    }

    @Override
    public String reserveVehicle(int vehicleId, int userId) {
        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
        if (vehicle == null) {
            return "Vehicle not found.";
        }

        if (!"available".equalsIgnoreCase(vehicle.getStatus())) {
            return "This vehicle is not available for reservation.";
        }

        boolean updated = vehicleRepository.updateVehicleStatus(vehicleId, "reserved");
        if (!updated) {
            return "Failed to update vehicle status.";
        }

        return "Reservation successful! Vehicle ID: " + vehicleId + " has been reserved.";
    }

    @Override
    public double getVehiclePrice(int vehicleId) {
        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
        return (vehicle != null) ? vehicle.getPrice() : -1;
    }
}
