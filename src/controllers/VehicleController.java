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
    public String getAllVehicles() {
        List<Vehicle> vehicles = repo.getAllVehicles();
        StringBuilder response = new StringBuilder("Каталог машин:\n");
        for (Vehicle vehicle : vehicles) {
            response.append(vehicle.toString()).append("\n");
        }
        return response.toString();
    }

    @Override
    public String getVehiclesByType(String vehicleType) {
        List<Vehicle> vehicles = repo.getVehiclesByType(vehicleType);
        if (vehicles.isEmpty()) return "Нет машин данного типа.";
        StringBuilder response = new StringBuilder("Машины типа ").append(vehicleType).append(":\n");
        for (Vehicle vehicle : vehicles) {
            response.append(vehicle.toString()).append("\n");
        }
        return response.toString();
    }

    @Override
    public String getVehiclesByBrand(String brand) {
        List<Vehicle> vehicles = repo.getVehiclesByBrand(brand);
        if (vehicles.isEmpty()) return "Нет машин данной марки.";
        StringBuilder response = new StringBuilder("Машины марки ").append(brand).append(":\n");
        for (Vehicle vehicle : vehicles) {
            response.append(vehicle.toString()).append("\n");
        }
        return response.toString();
    }

    @Override
    public String getVehicleById(int id) {
        Vehicle vehicle = repo.getVehicleById(id);
        return (vehicle != null) ? vehicle.toString() : "Машина с таким ID не найдена.";
    }

    @Override
    public boolean updateVehicleStatus(int id, String status) {
        return repo.updateVehicleStatus(id, status);
    }
}