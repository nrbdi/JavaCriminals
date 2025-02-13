package repositories;

import models.UserFinance;
import repositories.interfaces.IUserFinanceRepository;
import data.interfaces.IDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFinanceRepository implements IUserFinanceRepository {
    private final IDB db;

    public UserFinanceRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean updateUserBalance(int userId, double newBalance) {
        String query = "UPDATE public.\"users\" SET cash = ? WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, newBalance);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public double getUserBalance(int userId) {
        String query = "SELECT cash FROM public.\"users\" WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("cash");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Если пользователь не найден
    }
}
