package controllers.interfaces;

import models.Vehicle;
import models.Characteristics;

public interface IAdministrationController {
    boolean deleteUser(int userId);
    boolean addVehicle(Vehicle vehicle, Characteristics characteristics);
    boolean deleteVehicle(int vehicleId);
}
