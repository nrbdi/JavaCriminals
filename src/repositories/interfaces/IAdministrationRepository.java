package repositories.interfaces;

import models.Characteristics;
import models.Vehicle;

public interface IAdministrationRepository {
    boolean deleteUserById(int userId);
    boolean addVehicle(Vehicle vehicle, Characteristics characteristics);
    boolean deleteVehicleById(int vehicleId);
}
