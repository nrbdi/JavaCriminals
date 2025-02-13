package repositories;

import data.interfaces.IDB;
import models.Characteristics;
import models.User;
import models.Vehicle;
import repositories.interfaces.IAdministrationRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

                String charSql = "INSERT INTO public.\"Characteristics\" (vehicle_id, engine_power, fuel_type, transmission, color, mileage, camera_360, cruise_control, autopilot) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement charSt = conn.prepareStatement(charSql);
                charSt.setInt(1, vehicleId);
                charSt.setDouble(2, characteristics.getEnginePower());
                charSt.setString(3, characteristics.getFuelType());
                charSt.setString(4, characteristics.getTransmission());
                charSt.setString(5, characteristics.getColor());
                charSt.setDouble(6, characteristics.getMileage());
                charSt.setString(7, characteristics.getCamera360());
                charSt.setString(8, characteristics.getCruiseControl());
                charSt.setString(9, characteristics.getAutopilot());

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

    @Override
    public List<String[]> getPurchaseAndReservationReport() {
        List<String[]> report = new ArrayList<>();
        String sql = "SELECT u.id AS user_id, u.name AS user_name, u.email AS user_email, " +
                "v.brand AS vehicle_brand, v.model AS vehicle_model, v.price AS vehicle_price, " +
                "v.status AS vehicle_status, v.purchase_date " +
                "FROM public.\"users\" u " +
                "LEFT JOIN public.\"Vehicle\" v ON u.id = v.user_id " +
                "WHERE v.status IN ('reserved', 'sold')";

        try (Connection conn = db.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                report.add(new String[]{
                        String.valueOf(resultSet.getInt("user_id")),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_email"),
                        resultSet.getString("vehicle_brand"),
                        resultSet.getString("vehicle_model"),
                        String.format("$%.2f", resultSet.getDouble("vehicle_price")),
                        resultSet.getString("vehicle_status"),
                        resultSet.getDate("purchase_date") != null ?
                                resultSet.getDate("purchase_date").toLocalDate().toString() : "N/A"
                });
            }
        } catch (SQLException e) {
            System.out.println("SQL Error (getPurchaseAndReservationReport): " + e.getMessage());
        }
        return report;
    }

    @Override
    public List<String[]> getAllUsers() {
        List<String[]> users = new ArrayList<>();
        String query = "SELECT id, name, email, phone_number, cash FROM users WHERE role = 'user'";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new String[]{
                        String.valueOf(rs.getInt("id")),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        String.format("%.2f", rs.getDouble("cash"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public User getUserById(int id) {
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT * FROM public.\"users\" WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getDouble("cash")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<String[]> getAllAdministration() {
        List<String[]> adminsAndManagers = new ArrayList<>();
        String query = "SELECT id, name, email, role FROM users WHERE role IN ('admin', 'manager')";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                adminsAndManagers.add(new String[]{
                        String.valueOf(rs.getInt("id")),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("role")
                });
            }
        } catch (SQLException e) {
            System.out.println("SQL Error (getAllAdministration): " + e.getMessage());
        }

        return adminsAndManagers;
    }

}
