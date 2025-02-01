package controllers.interfaces;

public interface IVehicleController {
    String getAllVehicles();
    String getVehiclesByType(String vehicleType);
    String getVehiclesByBrand(String brand);
    String getVehicleStatus(int vehicleId);
    double getVehiclePrice(int vehicleId);
    String purchaseVehicle(int vehicleId, int userId, double userCash, double discount);
    String reserveVehicle(int vehicleId, int userId);
}
