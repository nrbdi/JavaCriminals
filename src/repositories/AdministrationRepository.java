package repositories;

import data.interfaces.IDB;
import models.Vehicle;
import models.Characteristics;
import repositories.interfaces.IAdministrationRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdministrationRepository implements IAdministrationRepository {
    private final IDB db;

    public AdministrationRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean deleteUserById(int userId) {
        try (Connection conn = db.getConnection()) {
            String sql = "DELETE FROM public.\"users\" WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, userId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error (deleteUserById): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean addVehicle(Vehicle vehicle, Characteristics characteristics) {
        try (Connection conn = db.getConnection()) {
            // Insert into Vehicle table
            String vehicleSql = "INSERT INTO public.\"Vehicle\" (brand, model, vehicle_type, price, release_year, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
            PreparedStatement vehicleSt = conn.prepareStatement(vehicleSql);
            vehicleSt.setString(1, vehicle.getBrand());
            vehicleSt.setString(2, vehicle.getModel());
            vehicleSt.setString(3, vehicle.getVehicleType());
            vehicleSt.setDouble(4, vehicle.getPrice());
            vehicleSt.setInt(5, vehicle.getReleaseYear());
            vehicleSt.setString(6, vehicle.getStatus());

            var rs = vehicleSt.executeQuery();
            if (rs.next()) {
                int vehicleId = rs.getInt("id");

                // Insert into Characteristics table
                String charSql = "INSERT INTO public.\"Characteristics\" (vehicle_id, engine_power, fuel_type, transmission, color, mileage) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement charSt = conn.prepareStatement(charSql);
                charSt.setInt(1, vehicleId);
                charSt.setDouble(2, characteristics.getEnginePower());
                charSt.setString(3, characteristics.getFuelType());
                charSt.setString(4, characteristics.getTransmission());
                charSt.setString(5, characteristics.getColor());
                charSt.setDouble(6, characteristics.getMileage());

                return charSt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error (addVehicle): " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteVehicleById(int vehicleId) {
        try (Connection conn = db.getConnection()) {
            // Delete from Characteristics table
            String charSql = "DELETE FROM public.\"Characteristics\" WHERE vehicle_id = ?";
            PreparedStatement charSt = conn.prepareStatement(charSql);
            charSt.setInt(1, vehicleId);
            charSt.executeUpdate();

            // Delete from Vehicle table
            String vehicleSql = "DELETE FROM public.\"Vehicle\" WHERE id = ?";
            PreparedStatement vehicleSt = conn.prepareStatement(vehicleSql);
            vehicleSt.setInt(1, vehicleId);
            return vehicleSt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error (deleteVehicleById): " + e.getMessage());
        }
        return false;
    }
}
