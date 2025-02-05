package controllers;

import controllers.interfaces.IAdministrationController;
import models.Vehicle;
import models.Characteristics;
import repositories.interfaces.IAdministrationRepository;

public class AdministrationController implements IAdministrationController {
    private final IAdministrationRepository adminRepository;

    public AdministrationController(IAdministrationRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public boolean deleteUser(int userId) {
        return adminRepository.deleteUserById(userId);
    }

    @Override
    public boolean addVehicle(Vehicle vehicle, Characteristics characteristics) {
        return adminRepository.addVehicle(vehicle, characteristics);
    }

    @Override
    public boolean deleteVehicle(int vehicleId) {
        return adminRepository.deleteVehicleById(vehicleId);
    }
}
