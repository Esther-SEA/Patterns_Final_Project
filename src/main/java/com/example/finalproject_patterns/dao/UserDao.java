package com.example.finalproject_patterns.dao;
import com.example.finalproject_patterns.Database;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class UserDao {

    private static final Logger logger =
            Logger.getLogger(UserDao.class.getName());

    public void createUser(String firstName, String lastName,
                           String email, String role) throws SQLException {

        String sql = "INSERT INTO users (first_name, last_name, email, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, role);

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Error creating user: " + e.getMessage());
            throw e;
        }
    }

    public Map<String, Object> getUser(int id) throws SQLException {

        String sql = "SELECT * FROM users WHERE id = ?";
        Map<String, Object> user = new HashMap<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    user.put("id", rs.getInt("id"));
                    user.put("first_name", rs.getString("first_name"));
                    user.put("last_name", rs.getString("last_name"));
                    user.put("email", rs.getString("email"));
                    user.put("role", rs.getString("role"));

                    return user;
                }
            }

        } catch (SQLException e) {
            logger.severe("Error getting user: " + e.getMessage());
            throw e;
        }

        return null;
    }

    public List<Map<String, Object>> getAllUsers() throws SQLException {

        String sql = "SELECT * FROM users";
        List<Map<String, Object>> users = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> user = new HashMap<>();

                user.put("id", rs.getInt("id"));
                user.put("first_name", rs.getString("first_name"));
                user.put("last_name", rs.getString("last_name"));
                user.put("email", rs.getString("email"));
                user.put("role", rs.getString("role"));

                users.add(user);
            }

        } catch (SQLException e) {
            logger.severe("Error getting users: " + e.getMessage());
            throw e;
        }

        return users;
    }

    public void updateUser(int id, String firstName,
                           String lastName, String email,
                           String role) throws SQLException {

        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, role = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, role);
            stmt.setInt(5, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Error updating user: " + e.getMessage());
            throw e;
        }
    }

    public void deleteUser(int id) throws SQLException {

        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Error deleting user: " + e.getMessage());
            throw e;
        }
    }
}
