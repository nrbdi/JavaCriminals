package repositories.interfaces;

import models.Characteristics;

import java.util.List;

public interface ICharacteristicsRepository {
    Characteristics getCharacteristicsByVehicleId(int vehicleId);
    List<Characteristics> getAllCharacteristics();
}
