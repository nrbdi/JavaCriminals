package repositories;

import data.interfaces.IDB;
import models.Vehicle;
import repositories.interfaces.IVehicleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepository implements IVehicleRepository {
    private final IDB db;

    public VehicleRepository(IDB db) {
        this.db = db;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT * FROM public.\"Vehicle\"";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("vehicle_type"),
                        rs.getDouble("price"),
                        rs.getInt("release_year"),
                        rs.getString("status")
                );
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> getVehiclesByType(String type) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT * FROM public.\"Vehicle\" WHERE vehicle_type = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, type);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("vehicle_type"),
                        rs.getDouble("price"),
                        rs.getInt("release_year"),
                        rs.getString("status")
                );
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> getVehiclesByBrand(String brand) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT * FROM public.\"Vehicle\" WHERE brand = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, brand);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("vehicle_type"),
                        rs.getDouble("price"),
                        rs.getInt("release_year"),
                        rs.getString("status")
                );
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return vehicles;
    }

    @Override
    public Vehicle getVehicleById(int id) {
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT * FROM public.\"Vehicle\" WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new Vehicle(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("vehicle_type"),
                        rs.getDouble("price"),
                        rs.getInt("release_year"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateVehicleStatus(int id, String status) {
        try (Connection conn = db.getConnection()) {
            String sql = "UPDATE public.\"Vehicle\" SET status = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, status);
            st.setInt(2, id);

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean createVehicle(Vehicle vehicle) {
        try (Connection conn = db.getConnection()) {
            String sql = "INSERT INTO public.\"Vehicle\" (brand, model, vehicle_type, price, release_year, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, vehicle.getBrand());
            st.setString(2, vehicle.getModel());
            st.setString(3, vehicle.getVehicleType());
            st.setDouble(4, vehicle.getPrice());
            st.setInt(5, vehicle.getReleaseYear());
            st.setString(6, vehicle.getStatus());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isVehicleAvailable(int id) {
        return false;
    }
}
