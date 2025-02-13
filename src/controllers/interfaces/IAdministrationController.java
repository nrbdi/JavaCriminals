package controllers.interfaces;

import models.User;

import java.util.List;

public interface IAdministrationController {
    String deleteUser(int userId);

    String addVehicle(String brand, String model, String type, double price, int year);

    String deleteVehicle(int vehicleId);

    String updateUser(int id, String name, String email, String phoneNumber, String password, String role, Double cash);

    String createAdminOrManager(String name, String email, String phoneNumber, String password, String role);

    List<String[]> getPurchaseAndReservationReport();

    String getAllUsers();

    User getUserById(int userId);
}
