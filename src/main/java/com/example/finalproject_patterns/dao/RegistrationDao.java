package com.example.finalproject_patterns.dao;
import com.example.finalproject_patterns.Database;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class RegistrationDao {

    private static final Logger logger =
            Logger.getLogger(RegistrationDao.class.getName());

    public void createRegistration(int userId, int conferenceId)
            throws SQLException {

        String checkUser = "SELECT id FROM users WHERE id = ?";
        String checkConference = "SELECT id FROM conferences WHERE id = ?";
        String checkDuplicate = "SELECT id FROM registrations WHERE user_id = ? AND conference_id = ?";

        String checkCapacity = "UPDATE conferences SET current_enrollment = current_enrollment + 1 WHERE id = ? AND current_enrollment < capacity";

        String getPosition = "SELECT COALESCE(MAX(waiting_position), 0) + 1 FROM registrations WHERE conference_id = ? AND status = 'WAITLISTED'";

        String insert = "INSERT INTO registrations (user_id, conference_id, status, waiting_position) VALUES (?, ?, ?, ?)";

        try (Connection conn = Database.getConnection()) {

            conn.setAutoCommit(false);

            try {
                try (PreparedStatement stmt = conn.prepareStatement(checkUser)) {
                    stmt.setInt(1, userId);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("User does not exist.");
                        }
                    }
                }

                try (PreparedStatement stmt = conn.prepareStatement(checkConference)) {
                    stmt.setInt(1, conferenceId);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Conference does not exist.");
                        }
                    }
                }

                try (PreparedStatement stmt = conn.prepareStatement(checkDuplicate)) {
                    stmt.setInt(1, userId);
                    stmt.setInt(2, conferenceId);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            throw new SQLException("User is already registered.");
                        }
                    }
                }

                int result;

                try (PreparedStatement stmt = conn.prepareStatement(checkCapacity)) {
                    stmt.setInt(1, conferenceId);
                    result = stmt.executeUpdate();
                }

                String status;
                int position = 0;

                if (result > 0) {
                    status = "CONFIRMED";
                } else {
                    status = "WAITLISTED";

                    try (PreparedStatement stmt = conn.prepareStatement(getPosition)) {
                        stmt.setInt(1, conferenceId);

                        try (ResultSet rs = stmt.executeQuery()) {
                            rs.next();
                            position = rs.getInt(1);
                        }
                    }
                }

                try (PreparedStatement stmt = conn.prepareStatement(insert)) {
                    stmt.setInt(1, userId);
                    stmt.setInt(2, conferenceId);
                    stmt.setString(3, status);
                    stmt.setInt(4, position);

                    stmt.executeUpdate();
                }

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                logger.severe("Error creating registration: " + e.getMessage());
                throw e;

            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            logger.severe("Database error: " + e.getMessage());
            throw e;
        }
    }

    public Map<String, Object> getRegistration(int id)
            throws SQLException {

        String sql = "SELECT * FROM registrations WHERE id = ?";
        Map<String, Object> registration = new HashMap<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    registration.put("id", rs.getInt("id"));
                    registration.put("user_id", rs.getInt("user_id"));
                    registration.put("conference_id", rs.getInt("conference_id"));
                    registration.put("status", rs.getString("status"));
                    registration.put("waiting_position", rs.getInt("waiting_position"));

                    return registration;
                }
            }

        } catch (SQLException e) {
            logger.severe("Error getting registration: " + e.getMessage());
            throw e;
        }

        return null;
    }

    public List<Map<String, Object>> getAllRegistrations()
            throws SQLException {

        String sql = "SELECT * FROM registrations";
        List<Map<String, Object>> registrations = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> registration = new HashMap<>();

                registration.put("id", rs.getInt("id"));
                registration.put("user_id", rs.getInt("user_id"));
                registration.put("conference_id", rs.getInt("conference_id"));
                registration.put("status", rs.getString("status"));
                registration.put("waiting_position", rs.getInt("waiting_position"));

                registrations.add(registration);
            }

        } catch (SQLException e) {
            logger.severe("Error getting registrations: " + e.getMessage());
            throw e;
        }

        return registrations;
    }

    public void updateRegistration(int id, String status,
                                   int waitingPosition)
            throws SQLException {

        String sql = "UPDATE registrations SET status = ?, waiting_position = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, waitingPosition);
            stmt.setInt(3, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Error updating registration: " + e.getMessage());
            throw e;
        }
    }

    public void deleteRegistration(int id) throws SQLException {

        String sql = "DELETE FROM registrations WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Error deleting registration: " + e.getMessage());
            throw e;
        }
    }
}

