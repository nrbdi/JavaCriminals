package repositories;

import data.interfaces.IDB;
import models.Vehicle;
import repositories.interfaces.IVehicleRepository;

import java.sql.*;
import java.time.LocalDate;
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
                vehicles.add(mapVehicle(rs));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return vehicles;
    }

    @Override
    public List<Vehicle> getVehiclesByType(String type) {
        return getVehiclesByField("vehicle_type", type);
    }

    @Override
    public List<Vehicle> getVehiclesByBrand(String brand) {
        return getVehiclesByField("brand", brand);
    }

    private List<Vehicle> getVehiclesByField(String field, String value) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT * FROM public.\"Vehicle\" WHERE " + field + " = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, value);
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
    public boolean updateVehicleStatus(int id, int userId, String status, LocalDate purchaseDate) {
        try (Connection conn = db.getConnection()) {
            String sql = "UPDATE public.\"Vehicle\" SET status = ?, user_id = ?, purchase_date = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, status);
            st.setInt(2, userId);
            if (purchaseDate != null) {
                st.setDate(3, Date.valueOf(purchaseDate));
            } else {
                st.setNull(3, Types.DATE);
            }
            st.setInt(4, id);

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
                    "VALUES (?, ?, ?, ?, ?, 'available')";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, vehicle.getBrand());
            st.setString(2, vehicle.getModel());
            st.setString(3, vehicle.getVehicleType());
            st.setDouble(4, vehicle.getPrice());
            st.setInt(5, vehicle.getReleaseYear());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isVehicleAvailable(int id) {
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT status FROM public.\"Vehicle\" WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return "available".equalsIgnoreCase(rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return false;
    }

    public void printJoinedTableView() {
        String sql = "SELECT u.id AS user_id, u.name AS user_name, u.email AS user_email, " +
                "v.brand AS vehicle_brand, v.model AS vehicle_model, v.price AS vehicle_price, " +
                "v.status AS vehicle_status, v.purchase_date " +
                "FROM public.\"users\" u " +
                "LEFT JOIN public.\"Vehicle\" v ON u.id = v.user_id " +
                "WHERE v.status IN ('reserved', 'sold')";

        try (Connection conn = db.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("=== Purchase & Reservation Report ===");

            boolean hasData = false;
            while (resultSet.next()) {
                hasData = true;
                System.out.printf("User: %s (%s), Vehicle: %s %s, Price: %.2f, Status: %s, Purchase Date: %s\n",
                        resultSet.getString("user_name"),
                        resultSet.getString("user_email"),
                        resultSet.getString("vehicle_brand"),
                        resultSet.getString("vehicle_model"),
                        resultSet.getDouble("vehicle_price"),
                        resultSet.getString("vehicle_status"),
                        resultSet.getDate("purchase_date") != null ?
                                resultSet.getDate("purchase_date").toLocalDate().toString() : "N/A"
                );
            }

            if (!hasData) {
                System.out.println("No purchased or reserved vehicles found.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
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
