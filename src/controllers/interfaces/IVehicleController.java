package controllers.interfaces;

public interface IVehicleController {
    String getAllVehicles();
    String getVehiclesByType(String vehicleType);
    String getVehiclesByBrand(String brand);
    String getVehicleStatus(int vehicleId);
    double getVehiclePrice(int vehicleId);
    String purchaseVehicle(int vehicleId, int userId);
    String reserveVehicle(int vehicleId, int userId);

    // Новый метод для вывода отчёта (объединённой таблицы)
    void showJoinedTableView();
}
