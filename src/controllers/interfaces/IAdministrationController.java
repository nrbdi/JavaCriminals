package controllers.interfaces;

public interface IAdministrationController {
    String deleteUser(int userId);

    String addVehicle(String brand, String model, String type, double price, int year);

    String deleteVehicle(int vehicleId);
}
