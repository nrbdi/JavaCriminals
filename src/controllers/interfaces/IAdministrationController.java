package controllers.interfaces;

import models.User;

import java.util.List;

public interface IAdministrationController {
    String deleteUser(int userId);

    String addVehicle(String brand, String model, String type, double price, int year,
                      String camera360, String cruiseControl, String autopilot,
                      double enginePower, String fuelType, String transmission, String color, double mileage);

    String deleteVehicle(int vehicleId);

    String updateUser(int id, String name, String email, String phoneNumber, String password, String role, Double cash);

    String createAdminOrManager(String name, String email, String phoneNumber, String password, String role);

    List<String[]> getPurchaseAndReservationReport();

    String getAllUsers();

    String getAllAdministration();

    User getUserById(int userId);
}
