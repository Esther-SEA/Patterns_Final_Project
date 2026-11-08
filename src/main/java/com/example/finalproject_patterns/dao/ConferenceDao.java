package com.example.finalproject_patterns.dao;
import com.example.finalproject_patterns.Database;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class ConferenceDao {

    private static final Logger logger =
            Logger.getLogger(ConferenceDao.class.getName());

    public void createConference(String title, String description,
                                 String location, int capacity,
                                 String startDate, String endDate)
            throws SQLException {

        String sql = "INSERT INTO conferences (title, description, location, capacity, start_date, end_date, current_enrollment) VALUES (?, ?, ?, ?, ?, ?, 0)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setString(3, location);
            stmt.setInt(4, capacity);
            stmt.setString(5, startDate);
            stmt.setString(6, endDate);

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Error creating conference: " + e.getMessage());
            throw e;
        }
    }

    public Map<String, Object> getConference(int id)
            throws SQLException {

        String sql = "SELECT * FROM conferences WHERE id = ?";
        Map<String, Object> conference = new HashMap<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    conference.put("id", rs.getInt("id"));
                    conference.put("title", rs.getString("title"));
                    conference.put("description", rs.getString("description"));
                    conference.put("location", rs.getString("location"));
                    conference.put("capacity", rs.getInt("capacity"));
                    conference.put("start_date", rs.getString("start_date"));
                    conference.put("end_date", rs.getString("end_date"));
                    conference.put("current_enrollment", rs.getInt("current_enrollment"));

                    return conference;
                }
            }

        } catch (SQLException e) {
            logger.severe("Error getting conference: " + e.getMessage());
            throw e;
        }

        return null;
    }

    public List<Map<String, Object>> getAllConferences()
            throws SQLException {

        String sql = "SELECT * FROM conferences";
        List<Map<String, Object>> conferences = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> conference = new HashMap<>();

                conference.put("id", rs.getInt("id"));
                conference.put("title", rs.getString("title"));
                conference.put("description", rs.getString("description"));
                conference.put("location", rs.getString("location"));
                conference.put("capacity", rs.getInt("capacity"));
                conference.put("start_date", rs.getString("start_date"));
                conference.put("end_date", rs.getString("end_date"));
                conference.put("current_enrollment", rs.getInt("current_enrollment"));

                conferences.add(conference);
            }

        } catch (SQLException e) {
            logger.severe("Error getting conferences: " + e.getMessage());
            throw e;
        }

        return conferences;
    }

    public void updateConference(int id, String title,
                                 String description, String location,
                                 int capacity, String startDate,
                                 String endDate) throws SQLException {

        String sql = "UPDATE conferences SET title = ?, description = ?, location = ?, capacity = ?, start_date = ?, end_date = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setString(3, location);
            stmt.setInt(4, capacity);
            stmt.setString(5, startDate);
            stmt.setString(6, endDate);
            stmt.setInt(7, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Error updating conference: " + e.getMessage());
            throw e;
        }
    }

    public void deleteConference(int id) throws SQLException {

        String sql = "DELETE FROM conferences WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.severe("Error deleting conference: " + e.getMessage());
            throw e;
        }
    }
}
