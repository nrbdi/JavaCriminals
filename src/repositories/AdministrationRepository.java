package repositories;

import data.interfaces.IDB;
import models.Characteristics;
import models.User;
import models.Vehicle;
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
            String vehicleSql = "INSERT INTO public.\"Vehicle\" (brand, model, vehicle_type, price, release_year, status) " +
                    "VALUES (?, ?, ?, ?, ?, 'available') RETURNING id";
            PreparedStatement vehicleSt = conn.prepareStatement(vehicleSql);
            vehicleSt.setString(1, vehicle.getBrand());
            vehicleSt.setString(2, vehicle.getModel());
            vehicleSt.setString(3, vehicle.getVehicleType());
            vehicleSt.setDouble(4, vehicle.getPrice());
            vehicleSt.setInt(5, vehicle.getReleaseYear());

            var rs = vehicleSt.executeQuery();
            if (rs.next()) {
                int vehicleId = rs.getInt("id");

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
            String charSql = "DELETE FROM public.\"Characteristics\" WHERE vehicle_id = ?";
            PreparedStatement charSt = conn.prepareStatement(charSql);
            charSt.setInt(1, vehicleId);
            charSt.executeUpdate();

            String vehicleSql = "DELETE FROM public.\"Vehicle\" WHERE id = ?";
            PreparedStatement vehicleSt = conn.prepareStatement(vehicleSql);
            vehicleSt.setInt(1, vehicleId);
            return vehicleSt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error (deleteVehicleById): " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        try (Connection conn = db.getConnection()) {
            String sql = "UPDATE public.\"users\" SET name = ?, email = ?, phone_number = ?, password = ?, role = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, user.getName());
            st.setString(2, user.getEmail());
            st.setString(3, user.getPhoneNumber());
            st.setString(4, user.getPassword());
            st.setString(5, user.getRole());
            st.setInt(6, user.getId());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error (updateUser): " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean createAdminOrManager(User user) {
        try (Connection conn = db.getConnection()) {
            String sql = "INSERT INTO public.\"users\" (name, email, phone_number, password, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, user.getName());
            st.setString(2, user.getEmail());
            st.setString(3, user.getPhoneNumber());
            st.setString(4, user.getPassword());
            st.setString(5, user.getRole());

            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error (createAdminOrManager): " + e.getMessage());
        }
        return false;
    }

}
