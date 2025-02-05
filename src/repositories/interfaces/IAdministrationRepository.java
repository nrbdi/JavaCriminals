package repositories.interfaces;

import models.Vehicle;
import models.Characteristics;

public interface IAdministrationRepository {
    boolean deleteUserById(int userId);
    boolean addVehicle(Vehicle vehicle, Characteristics characteristics);
    boolean deleteVehicleById(int vehicleId);
}
