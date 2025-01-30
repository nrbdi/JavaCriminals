package controllers;

import controllers.interfaces.IVehicleController;
import models.Vehicle;
import repositories.interfaces.IVehicleRepository;

import java.util.List;

public class VehicleController implements IVehicleController {
    private final IVehicleRepository repo;

    public VehicleController(IVehicleRepository repo) {
        this.repo = repo;
    }

    @Override
    public String createVehicle(String brand, String model, String vehicleType, double price, int releaseYear, String status) {
        Vehicle vehicle = new Vehicle(brand, model, vehicleType, price, releaseYear, status);
        boolean created = repo.createVehicle(vehicle);
        return created ? "Транспортное средство успешно добавлено." : "Ошибка при добавлении транспортного средства.";
    }

    @Override
    public String getAllVehicles() {
        List<Vehicle> vehicles = repo.getAllVehicles();
        if (vehicles.isEmpty()) {
            return "Нет доступных транспортных средств.";
        }

        StringBuilder response = new StringBuilder("Список транспортных средств:\n");
        for (Vehicle vehicle : vehicles) {
            response.append(vehicle).append("\n");
        }
        return response.toString();
    }

    @Override
    public String getVehiclesByType(String vehicleType) {
        List<Vehicle> vehicles = repo.getVehiclesByType(vehicleType);
        if (vehicles.isEmpty()) {
            return "Нет машин данного типа.";
        }

        StringBuilder response = new StringBuilder("Машины типа ").append(vehicleType).append(":\n");
        for (Vehicle vehicle : vehicles) {
            response.append(vehicle).append("\n");
        }
        return response.toString();
    }

    @Override
    public String getVehiclesByBrand(String brand) {
        List<Vehicle> vehicles = repo.getVehiclesByBrand(brand);
        if (vehicles.isEmpty()) {
            return "Нет машин данной марки.";
        }

        StringBuilder response = new StringBuilder("Машины марки ").append(brand).append(":\n");
        for (Vehicle vehicle : vehicles) {
            response.append(vehicle).append("\n");
        }
        return response.toString();
    }
}
