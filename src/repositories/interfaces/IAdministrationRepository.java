package repositories.interfaces;

import models.Characteristics;
import models.User;
import models.Vehicle;

import java.util.List;

public interface IAdministrationRepository {
    boolean deleteUserById(int userId);
    boolean addVehicle(Vehicle vehicle, Characteristics characteristics);
    boolean deleteVehicleById(int vehicleId);
    boolean updateUser(User user);
    boolean createAdminOrManager(User user);
    List<String[]> getPurchaseAndReservationReport();
    List<String[]> getAllUsers();
    User getUserById(int id);
}
