package controllers.interfaces;

public interface IVehicleController {
    String createVehicle(String brand, String model, String vehicleType, double price, int releaseYear, String status);
    String getAllVehicles();
    String getVehiclesByType(String vehicleType);
    String getVehiclesByBrand(String brand);
}
