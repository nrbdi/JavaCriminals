package controllers.interfaces;

public interface IVehicleController {
    String getAllVehicles();
    String getVehiclesByType(String vehicleType);
    String getVehiclesByBrand(String brand);
    String getVehicleStatus(int vehicleId); // Получение статуса машины
    double getVehiclePrice(int vehicleId); // Получение цены машины
    String purchaseVehicle(int vehicleId, int userId, double userCash, double discount);
    String reserveVehicle(int vehicleId, int userId);
}
