package controllers;

import controllers.interfaces.IAdministrationController;
import controllers.interfaces.IUserFinanceController;
import controllers.interfaces.IVehicleController;
import models.User;
import models.Vehicle;
import repositories.interfaces.IVehicleRepository;
import utils.Validator;

import java.time.LocalDate;
import java.util.List;

public class VehicleController implements IVehicleController {
    private final IVehicleRepository vehicleRepository;
    private final IAdministrationController adminController;
    private final IUserFinanceController financeController;

    public VehicleController(IVehicleRepository vehicleRepository, IAdministrationController adminController, IUserFinanceController financeController) {
        this.vehicleRepository = vehicleRepository;
        this.adminController = adminController;
        this.financeController = financeController;
    }

    @Override
    public String getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.getAllVehicles();
        if (vehicles.isEmpty()) {
            return "No vehicles available.";
        }

        String format = "| %-4s | %-10s | %-12s | %-12s | %-10s | %-12s | %-10s |%n";
        String separator = "+----+------------+--------------+--------------+------------+--------------+------------+%n";

        StringBuilder table = new StringBuilder();
        table.append(String.format(separator));
        table.append(String.format(format, "ID", "Brand", "Model", "Type", "Price", "Year", "Status"));
        table.append(String.format(separator));

        for (Vehicle vehicle : vehicles) {
            table.append(String.format(format,
                    vehicle.getId(),
                    vehicle.getBrand(),
                    vehicle.getModel(),
                    vehicle.getVehicleType(),
                    String.format("%.2f", vehicle.getPrice()),
                    vehicle.getReleaseYear(),
                    vehicle.getStatus()
            ));
        }

        table.append(String.format(separator));

        return table.toString();
    }


    @Override
    public String getVehicleDetailsById(int vehicleId) {
        if (!Validator.isPositiveInteger(vehicleId)) {
            return "Error: Vehicle ID must be a positive number.";
        }

        String vehicleDetails = vehicleRepository.getVehicleDetailsById(vehicleId);
        if (vehicleDetails.equals("Vehicle not found.")) {
            return "Vehicle not found.";
        }

        return "Vehicle Details:\n" + vehicleDetails;
    }


    @Override
    public String getVehiclesByType(String vehicleType) {
        List<Vehicle> vehicles = vehicleRepository.getVehiclesByType(vehicleType);
        if (vehicles.isEmpty()) {
            return "No vehicles found for the type: " + vehicleType;
        }

        String format = "| %-4s | %-10s | %-12s | %-10s | %-6s | %-10s |%n";
        String separator = "+----+------------+--------------+------------+------+------------+%n";

        StringBuilder table = new StringBuilder();
        table.append(String.format("Vehicles of type %s:\n", vehicleType));
        table.append(String.format(separator));
        table.append(String.format(format, "ID", "Brand", "Model", "Price", "Year", "Status"));
        table.append(String.format(separator));

        for (Vehicle vehicle : vehicles) {
            table.append(String.format(format,
                    vehicle.getId(),
                    vehicle.getBrand(),
                    vehicle.getModel(),
                    String.format("%.2f", vehicle.getPrice()),
                    vehicle.getReleaseYear(),
                    vehicle.getStatus()
            ));
        }

        table.append(String.format(separator));

        return table.toString();
    }


    @Override
    public String getVehiclesByBrand(String brand) {
        List<Vehicle> vehicles = vehicleRepository.getVehiclesByBrand(brand);
        if (vehicles.isEmpty()) {
            return "No vehicles found for the brand: " + brand;
        }

        String format = "| %-4s | %-12s | %-10s | %-10s | %-6s | %-10s |%n";
        String separator = "+----+--------------+------------+------------+------+------------+%n";

        StringBuilder table = new StringBuilder();
        table.append(String.format("Vehicles of brand %s:\n", brand));
        table.append(String.format(separator));
        table.append(String.format(format, "ID", "Model", "Type", "Price", "Year", "Status"));
        table.append(String.format(separator));

        for (Vehicle vehicle : vehicles) {
            table.append(String.format(format,
                    vehicle.getId(),
                    vehicle.getModel(),
                    vehicle.getVehicleType(),
                    String.format("%.2f", vehicle.getPrice()),
                    vehicle.getReleaseYear(),
                    vehicle.getStatus()
            ));
        }

        table.append(String.format(separator));

        return table.toString();
    }


    @Override
    public String getVehicleStatus(int vehicleId) {
        if (!Validator.isPositiveInteger(vehicleId)) {
            return "Error: Vehicle ID must be a positive number.";
        }

        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
        if (vehicle == null) {
            return "Vehicle not found.";
        }
        return vehicle.getStatus();
    }

    @Override
    public String getVehicleById(int vehicleId) {
        if (!Validator.isPositiveInteger(vehicleId)) {
            return "Error: Vehicle ID must be a positive number.";
        }

        String vehicleDetails = vehicleRepository.getVehicleDetailsById(vehicleId);
        if (vehicleDetails.equals("Vehicle not found.")) {
            return "Vehicle not found.";
        }

        return vehicleDetails;
    }


    @Override
    public String purchaseVehicle(int vehicleId, int userId) {
        if (!Validator.isPositiveInteger(vehicleId)) {
            return "Error: Vehicle ID must be a positive number.";
        }
        if (!Validator.isPositiveInteger(userId)) {
            return "Error: User ID must be a positive number.";
        }

        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
        User user = adminController.getUserById(userId);

        if (vehicle == null) return "Vehicle not found.";
        if (user == null) return "User not found.";
        if (!"available".equalsIgnoreCase(vehicle.getStatus())) return "This vehicle is not available for purchase.";
        if (user.getCash() < vehicle.getPrice()) return "Insufficient funds to purchase this vehicle.";

        double newBalance = user.getCash() - vehicle.getPrice();
        boolean balanceUpdated = financeController.updateUserBalance(userId, newBalance);
        if (!balanceUpdated) return "Failed to update user balance.";

        boolean vehicleUpdated = vehicleRepository.updateVehicleStatus(vehicleId, userId, "sold", LocalDate.now());
        if (!vehicleUpdated) return "Failed to update vehicle status.";

        return String.format(
                "Purchase successful!\nVehicle Details:\n- Brand: %s\n- Model: %s\n- Price: %.2f\n- Purchase Date: %s\nYour new balance: %.2f",
                vehicle.getBrand(), vehicle.getModel(), vehicle.getPrice(), LocalDate.now(), newBalance
        );
    }

    @Override
    public String reserveVehicle(int vehicleId, int userId) {
        if (!Validator.isPositiveInteger(vehicleId)) {
            return "Error: Vehicle ID must be a positive number.";
        }
        if (!Validator.isPositiveInteger(userId)) {
            return "Error: User ID must be a positive number.";
        }

        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
        User user = adminController.getUserById(userId);

        if (vehicle == null) return "Vehicle not found.";
        if (user == null) return "User not found.";
        if (!"available".equalsIgnoreCase(vehicle.getStatus())) return "This vehicle is not available for reservation.";

        boolean updated = vehicleRepository.updateVehicleStatus(vehicleId, userId, "reserved", LocalDate.now());
        if (!updated) return "Failed to update vehicle status.";

        return String.format("Reservation successful! Vehicle ID: %d has been reserved on %s.", vehicleId, LocalDate.now());
    }

    @Override
    public double getVehiclePrice(int vehicleId) {
        if (!Validator.isPositiveInteger(vehicleId)) {
            return -1;
        }

        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
        return (vehicle != null) ? vehicle.getPrice() : -1;
    }

    @Override
    public void showJoinedTableView() {
        vehicleRepository.printJoinedTableView();
    }
}