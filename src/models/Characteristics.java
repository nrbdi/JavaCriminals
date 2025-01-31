package models;

public class Characteristics {
    private int id;
    private int vehicleId;
    private double enginePower;
    private String fuelType;
    private String transmission;
    private String color;
    private double mileage;

    public Characteristics(int id, int vehicleId, double enginePower, String fuelType, String transmission, String color, double mileage) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.enginePower = enginePower;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.color = color;
        this.mileage = mileage;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nEngine Power: " + enginePower + " L" +
                "\nFuel Type: " + fuelType +
                "\nTransmission: " + transmission +
                "\nColor: " + color +
                "\nMileage: " + mileage + " km";
    }
}
