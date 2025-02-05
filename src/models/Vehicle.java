package models;

public class Vehicle {
    private int id;
    private String brand;
    private String model;
    private String vehicleType;
    private double price;
    private int releaseYear;
    private String status;

    public Vehicle(int id, String brand, String model, String vehicleType, double price, int releaseYear, String status) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.vehicleType = vehicleType;
        this.price = price;
        this.releaseYear = releaseYear;
        this.status = status;
    }

    public Vehicle(String brand, String model, String vehicleType, double price, int releaseYear, String status) {
        this.brand = brand;
        this.model = model;
        this.vehicleType = vehicleType;
        this.price = price;
        this.releaseYear = releaseYear;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d\nBrand: %s\nModel: %s\nType: %s\nPrice: %.2f\nRelease Year: %d\nStatus: %s\n",
                id, brand, model, vehicleType, price, releaseYear, status
        );
    }
}
