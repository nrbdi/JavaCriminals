package models;

public class Characteristics {
    private int id;
    private int vehicleId;
    private double enginePower;
    private String fuelType;
    private String transmission;
    private String color;
    private double mileage;

    public Characteristics(int id, double enginePower, String fuelType, String transmission, String color, double mileage) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.enginePower = enginePower;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.color = color;
        this.mileage = mileage;
    }

    public int getId() {
        return id;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public double getEnginePower() {
        return enginePower;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getColor() {
        return color;
    }

    public double getMileage() {
        return mileage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setEnginePower(double enginePower) {
        this.enginePower = enginePower;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    @Override
    public String toString() {
        return String.format("Engine Power: %.1f L, Fuel Type: %s, Transmission: %s, Color: %s, Mileage: %.1f km",
                enginePower, fuelType, transmission, color, mileage);
    }
}
