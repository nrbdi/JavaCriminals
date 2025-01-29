package controllers.interfaces;

public interface IVehicleController {
    String getAllVehicles();
    String getVehiclesByType(String vehicleType);
    String getVehiclesByBrand(String brand);
    String getVehicleById(int id);
    boolean updateVehicleStatus(int id, String status);
}
