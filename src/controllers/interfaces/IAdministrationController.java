package controllers.interfaces;

import models.Characteristics;
import models.Vehicle;

public interface IAdministrationController {
    boolean deleteUser(int userId);
    boolean addVehicle(Vehicle vehicle, Characteristics characteristics);
    boolean deleteVehicle(int vehicleId);
}
