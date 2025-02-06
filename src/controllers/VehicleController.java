package controllers;

import controllers.interfaces.IUserController;
import controllers.interfaces.IVehicleController;
import models.User;
import models.Vehicle;
import repositories.interfaces.IVehicleRepository;

import java.time.LocalDate;
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
    public String getVehiclesByType(String vehicleType) {
        List<Vehicle> vehicles = vehicleRepository.getVehiclesByType(vehicleType);
        if (vehicles.isEmpty()) {
            return "No vehicles found for the type: " + vehicleType;
        }

        StringBuilder sb = new StringBuilder("Vehicles of type " + vehicleType + ":\n");
        for (Vehicle vehicle : vehicles) {
            sb.append(String.format(
                    "ID: %d, Brand: %s, Model: %s, Price: %.2f, Status: %s\n",
                    vehicle.getId(),
                    vehicle.getBrand(),
                    vehicle.getModel(),
                    vehicle.getPrice(),
                    vehicle.getStatus()
            ));
        }
        return sb.toString();
    }

    @Override
    public String getVehiclesByBrand(String brand) {
        List<Vehicle> vehicles = vehicleRepository.getVehiclesByBrand(brand);
        if (vehicles.isEmpty()) {
            return "No vehicles found for the brand: " + brand;
        }

        StringBuilder sb = new StringBuilder("Vehicles of brand " + brand + ":\n");
        for (Vehicle vehicle : vehicles) {
            sb.append(String.format(
                    "ID: %d, Model: %s, Type: %s, Price: %.2f, Status: %s\n",
                    vehicle.getId(),
                    vehicle.getModel(),
                    vehicle.getVehicleType(),
                    vehicle.getPrice(),
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
    public String purchaseVehicle(int vehicleId, int userId) {
        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
        User user = userController.getUserById(userId);

        if (vehicle == null) return "Vehicle not found.";
        if (user == null) return "User not found.";
        if (!"available".equalsIgnoreCase(vehicle.getStatus())) return "This vehicle is not available for purchase.";
        if (user.getCash() < vehicle.getPrice()) return "Insufficient funds to purchase this vehicle.";

        // Обновляем баланс пользователя
        double newBalance = user.getCash() - vehicle.getPrice();
        boolean balanceUpdated = userController.updateUserBalance(userId, newBalance);
        if (!balanceUpdated) return "Failed to update user balance.";

        // Обновляем статус автомобиля + добавляем дату покупки
        boolean vehicleUpdated = vehicleRepository.updateVehicleStatus(vehicleId, userId, "sold", LocalDate.now());
        if (!vehicleUpdated) return "Failed to update vehicle status.";

        return String.format(
                "Purchase successful!\nVehicle Details:\n- Brand: %s\n- Model: %s\n- Price: %.2f\n- Purchase Date: %s\nYour new balance: %.2f",
                vehicle.getBrand(), vehicle.getModel(), vehicle.getPrice(), LocalDate.now(), newBalance
        );
    }

    @Override
    public String reserveVehicle(int vehicleId, int userId) {
        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
        User user = userController.getUserById(userId);

        if (vehicle == null) return "Vehicle not found.";
        if (user == null) return "User not found.";
        if (!"available".equalsIgnoreCase(vehicle.getStatus())) return "This vehicle is not available for reservation.";

        // Обновляем статус автомобиля на "reserved"
        boolean updated = vehicleRepository.updateVehicleStatus(vehicleId, userId, "reserved", null);
        if (!updated) return "Failed to update vehicle status.";

        return String.format("Reservation successful! Vehicle ID: %d has been reserved.", vehicleId);
    }

    @Override
    public double getVehiclePrice(int vehicleId) {
        Vehicle vehicle = vehicleRepository.getVehicleById(vehicleId);
        return (vehicle != null) ? vehicle.getPrice() : -1;
    }

    // Новый метод для вызова объединённой таблицы (отчёта)
    @Override
    public void showJoinedTableView() {
        vehicleRepository.printJoinedTableView();
    }
}
