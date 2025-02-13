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
            return "No characteristics found for this vehicle.";
        }
        return String.format(
                "Engine Power: %.1f L\nFuel Type: %s\nTransmission: %s\nColor: %s\nMileage: %.1f km\n360 Camera: %s\nCruise Control: %s\nAutopilot: %s",
                characteristics.getEnginePower(),
                characteristics.getFuelType(),
                characteristics.getTransmission(),
                characteristics.getColor(),
                characteristics.getMileage(),
                characteristics.getCamera360(),
                characteristics.getCruiseControl(),
                characteristics.getAutopilot()
        );
    }
}
