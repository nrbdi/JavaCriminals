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
            String sql = "SELECT * FROM public.\"Vehicle\" ORDER BY id ASC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                vehicles.add(mapVehicle(rs));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> getVehiclesByType(String vehicleType) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT * FROM public.\"Vehicle\" WHERE vehicle_type = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, vehicleType);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                vehicles.add(mapVehicle(rs));
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
                vehicles.add(mapVehicle(rs));
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
                return mapVehicle(rs);
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

    private Vehicle mapVehicle(ResultSet rs) throws SQLException {
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
}