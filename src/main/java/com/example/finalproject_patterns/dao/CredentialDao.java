package com.example.finalproject_patterns.dao;
import com.example.finalproject_patterns.Database;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class CredentialDao {

    private static final Logger logger =
            Logger.getLogger(CredentialDao.class.getName());

    public void createCredential(int userId, String username,
                                 String passwordHash) throws SQLException {

        String sql = "INSERT INTO credentials (user_id, username, password_hash) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, username);
            stmt.setString(3, passwordHash);

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Error creating credential: " + e.getMessage());
            throw e;
        }
    }

    public Map<String, Object> getCredential(String username)
            throws SQLException {

        String sql = "SELECT * FROM credentials WHERE username = ?";
        Map<String, Object> credential = new HashMap<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    credential.put("id", rs.getInt("id"));
                    credential.put("user_id", rs.getInt("user_id"));
                    credential.put("username", rs.getString("username"));
                    credential.put("password_hash", rs.getString("password_hash"));

                    return credential;
                }
            }

        } catch (SQLException e) {
            logger.severe("Error getting credential: " + e.getMessage());
            throw e;
        }

        return null;
    }

    public List<Map<String, Object>> getAllCredentials()
            throws SQLException {

        String sql = "SELECT * FROM credentials";
        List<Map<String, Object>> credentials = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> credential = new HashMap<>();

                credential.put("id", rs.getInt("id"));
                credential.put("user_id", rs.getInt("user_id"));
                credential.put("username", rs.getString("username"));
                credential.put("password_hash", rs.getString("password_hash"));

                credentials.add(credential);
            }

        } catch (SQLException e) {
            logger.severe("Error getting credentials: " + e.getMessage());
            throw e;
        }

        return credentials;
    }

    public void updateCredential(int id, String username,
                                 String passwordHash) throws SQLException {

        String sql = "UPDATE credentials SET username = ?, password_hash = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            stmt.setInt(3, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Error updating credential: " + e.getMessage());
            throw e;
        }
    }

    public void deleteCredential(int id) throws SQLException {

        String sql = "DELETE FROM credentials WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Error deleting credential: " + e.getMessage());
            throw e;
        }
    }
}

