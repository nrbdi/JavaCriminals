package controllers;

import controllers.interfaces.ICharacteristicsController;
import models.Characteristics;
import repositories.interfaces.ICharacteristicsRepository;

public class CharacteristicsController implements ICharacteristicsController {
    private final ICharacteristicsRepository repo;

    public CharacteristicsController(ICharacteristicsRepository repo) {
        this.repo = repo;
    }

    @Override
    public String getCharacteristicsByVehicleId(int vehicleId) {
        Characteristics characteristics = repo.getCharacteristicsByVehicleId(vehicleId);
        if (characteristics == null) {
            return null;
        }
        return characteristics.toString();
    }
}
