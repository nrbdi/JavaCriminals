package repositories.interfaces;

import models.Vehicle;
import java.util.List;

public interface IVehicleRepository {
    List<Vehicle> getAllVehicles();
    List<Vehicle> getVehiclesByType(String vehicleType);
    List<Vehicle> getVehiclesByBrand(String brand);
    Vehicle getVehicleById(int id);
    boolean updateVehicleStatus(int id, String status);
    boolean createVehicle(Vehicle vehicle);
    boolean isVehicleAvailable(int id); // Добавлен метод для проверки доступности
}
