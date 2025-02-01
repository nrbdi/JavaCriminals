package repositories;

import data.interfaces.IDB;
import models.Characteristics;
import repositories.interfaces.ICharacteristicsRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CharacteristicsRepository implements ICharacteristicsRepository {
    private final IDB db;

    public CharacteristicsRepository(IDB db) {
        this.db = db;
    }

    @Override
    public Characteristics getCharacteristicsByVehicleId(int vehicleId) {
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT * FROM public.\"Characteristics\" WHERE vehicle_id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, vehicleId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Characteristics(
                        rs.getInt("id"),
                        rs.getInt("vehicle_id"),
                        rs.getDouble("engine_power"),
                        rs.getString("fuel_type"),
                        rs.getString("transmission"),
                        rs.getString("color"),
                        rs.getDouble("mileage")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Characteristics> getAllCharacteristics() {
        List<Characteristics> characteristicsList = new ArrayList<>();
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT * FROM public.\"Characteristics\"";
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                characteristicsList.add(new Characteristics(
                        rs.getInt("id"),
                        rs.getInt("vehicle_id"),
                        rs.getDouble("engine_power"),
                        rs.getString("fuel_type"),
                        rs.getString("transmission"),
                        rs.getString("color"),
                        rs.getDouble("mileage")
                ));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return characteristicsList;
    }
}
