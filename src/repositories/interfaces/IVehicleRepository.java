package repositories.interfaces;

import models.Vehicle;

import java.time.LocalDate;
import java.util.List;

public interface IVehicleRepository {
    List<Vehicle> getAllVehicles();
    List<Vehicle> getVehiclesByType(String vehicleType);
    List<Vehicle> getVehiclesByBrand(String brand);
    Vehicle getVehicleById(int id);

    // Обновлённый метод: теперь принимает userId и дату покупки
    boolean updateVehicleStatus(int id, int userId, String status, LocalDate purchaseDate);

    boolean createVehicle(Vehicle vehicle);
    boolean isVehicleAvailable(int id);

    // Новый метод для объединённой таблицы (отчёт о покупках/бронированиях)
    void printJoinedTableView();
}
