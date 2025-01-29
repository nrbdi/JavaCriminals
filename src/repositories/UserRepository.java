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
        Connection conn = null;
        try {
            conn = db.getConnection();
            String sql = "INSERT INTO users (name, email, phone_number, password, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, user.getName());
            st.setString(2, user.getEmail());
            st.setString(3, user.getPhoneNumber());
            st.setString(4, user.getPassword());
            st.setString(5, user.getRole());

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public User getUserById(int id) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            String sql = "SELECT * FROM users WHERE id = ?";
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
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        Connection conn = null;
        try {
            conn = db.getConnection();
            String sql = "SELECT * FROM users";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("password"),
                        rs.getString("role")
                );
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }
    @Override
    public User getUserByEmail(String email) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            String sql = "SELECT * FROM users WHERE email = ?";
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
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }


    @Override
    public boolean updateUser(User user) {
        Connection conn = null;
        try {
            conn = db.getConnection();
            String sql = "UPDATE users SET name = ?, email = ?, phone_number = ?, password = ?, role = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, user.getName());
            st.setString(2, user.getEmail());
            st.setString(3, user.getPhoneNumber());
            st.setString(4, user.getPassword());
            st.setString(5, user.getRole());
            st.setInt(6, user.getId());

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return false;
    }
}
