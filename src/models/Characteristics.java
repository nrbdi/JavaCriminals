package models;

public class Characteristics {
    private int id;
    private int vehicleId;
    private double enginePower;
    private String fuelType;
    private String transmission;
    private String color;
    private double mileage;
    private String camera360;
    private String cruiseControl;
    private String autopilot;

    public Characteristics(int id, double enginePower, String fuelType, String transmission, String color, double mileage, String camera360, String cruiseControl, String autopilot) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.enginePower = enginePower;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.color = color;
        this.mileage = mileage;
        this.camera360 = camera360;
        this.cruiseControl = cruiseControl;
        this.autopilot = autopilot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public double getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(double enginePower) {
        this.enginePower = enginePower;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public String getCamera360() {
        return camera360;
    }

    public void setCamera360(String camera360) {
        this.camera360 = camera360;
    }

    public String getCruiseControl() {
        return cruiseControl;
    }

    public void setCruiseControl(String cruiseControl) {
        this.cruiseControl = cruiseControl;
    }

    public String getAutopilot() {
        return autopilot;
    }

    public void setAutopilot(String autopilot) {
        this.autopilot = autopilot;
    }

    @Override
    public String toString() {
        return String.format("Engine Power: %.1f L, Fuel Type: %s, Transmission: %s, Color: %s, Mileage: %.1f km, 360 Camera: %s, Cruise Control: %s, Autopilot: %s",
                enginePower, fuelType, transmission, color, mileage, camera360, cruiseControl, autopilot);
    }
}