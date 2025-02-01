package repositories;

import data.interfaces.IDB;
import models.User;
import repositories.interfaces.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createUser(User user) {
        try (Connection conn = db.getConnection()) {
            String sql = "INSERT INTO public.\"users\" (name, email, phone_number, password, role, cash) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, user.getName());
            st.setString(2, user.getEmail());
            st.setString(3, user.getPhoneNumber());
            st.setString(4, user.getPassword());
            st.setString(5, user.getRole());
            st.setDouble(6, user.getCash());

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        try (Connection conn = db.getConnection()) {
            String sql = "UPDATE public.\"users\" SET name = ?, email = ?, phone_number = ?, password = ?, role = ?, cash = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, user.getName());
            st.setString(2, user.getEmail());
            st.setString(3, user.getPhoneNumber());
            st.setString(4, user.getPassword());
            st.setString(5, user.getRole());
            st.setDouble(6, user.getCash());
            st.setInt(7, user.getId());

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return false;
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
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT * FROM public.\"users\"";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getDouble("cash")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return users;
    }

    @Override
    public User getUserByEmail(String email) {
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT * FROM public.\"users\" WHERE email = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, email);
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
    public boolean updateUserBalance(int userId, double newBalance) {
        try (Connection conn = db.getConnection()) {
            String sql = "UPDATE public.\"users\" SET cash = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setDouble(1, newBalance);
            st.setInt(2, userId);

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return false;
    }
}
